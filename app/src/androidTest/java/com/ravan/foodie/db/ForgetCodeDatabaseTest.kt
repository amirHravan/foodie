package com.ravan.foodie.db

import android.content.Context
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ravan.foodie.reserveinfo.db.ForgetCodeDatabase
import com.ravan.foodie.reserveinfo.db.ForgetCodeMigration1To2
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForgetCodeDatabaseTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val databaseName = "forget-code-migration-test"

    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        ForgetCodeDatabase::class.java,
    )

    private var database: ForgetCodeDatabase? = null

    @After
    fun tearDown() {
        database?.close()
        context.deleteDatabase(databaseName)
    }

    private fun createVersion1() {
        helper.createDatabase(databaseName, 1).apply {
            execSQL(
                "INSERT INTO forget_code_table (reserveId, code, timestamp, isValid) " +
                    "VALUES (10, '1234', 0, 1)"
            )
            close()
        }
    }

    @Test
    fun migrationMatchesTheVersion2Schema() {
        createVersion1()

        helper.runMigrationsAndValidate(
            databaseName, 2, true, ForgetCodeMigration1To2 { "me" }
        ).close()
    }

    @Test
    fun existingCodesBelongToTheLegacyAccount() = runBlocking {
        createVersion1()
        val dao = Room.databaseBuilder(context, ForgetCodeDatabase::class.java, databaseName)
            .addMigrations(ForgetCodeMigration1To2 { "me" })
            .build()
            .also { database = it }
            .forgetCodeDao()

        assertEquals(listOf(10), dao.getReserveIds("me"))

        dao.deleteForAccount("me")
        assertEquals(emptyList<Int>(), dao.getAllForgetCodes().map { it.reserveId })
    }
}
