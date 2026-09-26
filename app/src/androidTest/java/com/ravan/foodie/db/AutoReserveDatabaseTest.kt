package com.ravan.foodie.db

import android.content.Context
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ravan.foodie.autoreserve.db.AutoReserveDataBase
import com.ravan.foodie.autoreserve.db.AutoReserveMigration1To2
import com.ravan.foodie.autoreserve.db.PrepopulateFoodsDatabase
import com.ravan.foodie.autoreserve.db.model.TEMPLATE_ACCOUNT_ID
import com.ravan.foodie.autoreserve.domain.repository.AutoReserveRepositoryImplementation
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AutoReserveDatabaseTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val databaseName = "auto-reserve-migration-test"

    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AutoReserveDataBase::class.java,
    )

    private var database: AutoReserveDataBase? = null

    @After
    fun tearDown() {
        database?.close()
        context.deleteDatabase(databaseName)
    }

    private fun createVersion1() {
        helper.createDatabase(databaseName, 1).apply {
            execSQL("INSERT INTO food_priority (id, name, priority) VALUES (1, 'kabab', 5)")
            execSQL("INSERT INTO food_priority (id, name, priority) VALUES (2, 'gheyme', 0)")
            execSQL("INSERT INTO food_priority (id, name, priority) VALUES (3, 'pizza', 3)")
            execSQL("INSERT INTO auto_reserve_days (id, days) VALUES (0, 'SATURDAY,MONDAY')")
            close()
        }
    }

    private fun openMigrated(legacyUsername: String?): AutoReserveDataBase =
        Room.databaseBuilder(context, AutoReserveDataBase::class.java, databaseName)
            .addMigrations(AutoReserveMigration1To2 { legacyUsername })
            .build()
            .also { database = it }

    @Test
    fun migrationMatchesTheVersion2Schema() {
        createVersion1()

        helper.runMigrationsAndValidate(
            databaseName, 2, true, AutoReserveMigration1To2 { "me" }
        ).close()
    }

    @Test
    fun migrationMovesExistingDataToTheLegacyAccount() = runBlocking {
        createVersion1()
        val dao = openMigrated(legacyUsername = "me").foodDao()

        val mine = dao.getAllFoods("me")
        assertEquals(listOf("kabab" to 5, "gheyme" to 0, "pizza" to 3), mine.map { it.name to it.priority })
        assertEquals(listOf("SATURDAY", "MONDAY"), dao.getAutoReserveDays("me")?.days)

        val template = dao.getAllFoods(TEMPLATE_ACCOUNT_ID)
        assertEquals(listOf("kabab", "gheyme", "pizza"), template.map { it.name })
        assertTrue(template.all { it.priority == 0 })
    }

    @Test
    fun migrationWithoutALegacyAccountKeepsOnlyTheTemplate() = runBlocking {
        createVersion1()
        val dao = openMigrated(legacyUsername = null).foodDao()

        assertEquals(3, dao.countFoods(TEMPLATE_ACCOUNT_ID))
        assertEquals(0, dao.countFoods("me"))
        assertEquals(null, dao.getAutoReserveDays(TEMPLATE_ACCOUNT_ID))
    }

    @Test
    fun freshDatabaseIsSeededFromTheBundledFoodList() = runBlocking {
        val db = Room.inMemoryDatabaseBuilder(context, AutoReserveDataBase::class.java)
            .addCallback(PrepopulateFoodsDatabase(context))
            .build()
            .also { database = it }

        val template = db.foodDao().getAllFoods(TEMPLATE_ACCOUNT_ID)

        assertEquals(64, template.size)
        assertEquals("خوراک فیله سوخاری", template.first().name)
    }

    @Test
    fun accountsGetTheirOwnCopyAndDeletingOneLeavesTheOthers() = runBlocking {
        val db = Room.inMemoryDatabaseBuilder(context, AutoReserveDataBase::class.java)
            .addCallback(PrepopulateFoodsDatabase(context))
            .build()
            .also { database = it }
        var active = "me"
        val repository = AutoReserveRepositoryImplementation(db) { active }

        // First use seeds from the defaults.
        val first = repository.getAllFoodPriorities().first()
        repository.updateFoodPriority(first.id, 4)
        repository.updateReserveDays(listOf("SUNDAY"))

        // A new account copies the active one.
        repository.copyAccountData(fromUsername = "me", toUsername = "ali")
        active = "ali"
        assertEquals(4, repository.getAllFoodPriorities().first().priority)
        assertEquals(listOf("SUNDAY"), repository.getAllReserveDays()?.days)

        // Changing one account doesn't touch the other.
        repository.updateFoodPriority(repository.getAllFoodPriorities().first().id, 1)
        active = "me"
        assertEquals(4, repository.getAllFoodPriorities().first().priority)

        repository.deleteAccountData("ali")
        assertEquals(0, db.foodDao().countFoods("ali"))
        assertEquals(null, db.foodDao().getAutoReserveDays("ali"))
        assertEquals(64, db.foodDao().countFoods("me"))
    }
}
