package com.ravan.foodie.autoreserve.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.OnConflictStrategy
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ravan.foodie.autoreserve.db.model.TEMPLATE_ACCOUNT_ID
import java.io.File

/**
 * Fills the template rows from the bundled food list when the database is first created.
 *
 * This replaces Room's createFromAsset: the asset has the old single-account schema, and a copied
 * asset is never migrated (it goes through Room's create path), so it would fail validation.
 */
class PrepopulateFoodsDatabase(
    private val context: Context,
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        val seedFile = File(context.cacheDir, "food_priority_seed.db")
        try {
            context.assets.open(AutoReserveDataBase.SEED_ASSET).use { input ->
                seedFile.outputStream().use { input.copyTo(it) }
            }
            SQLiteDatabase.openDatabase(seedFile.path, null, SQLiteDatabase.OPEN_READONLY)
                .use { seed ->
                    seed.rawQuery("SELECT name, priority FROM food_priority ORDER BY id", null)
                        .use { cursor ->
                            while (cursor.moveToNext()) {
                                db.insert(
                                    "food_priority",
                                    OnConflictStrategy.REPLACE,
                                    ContentValues().apply {
                                        put("accountId", TEMPLATE_ACCOUNT_ID)
                                        put("name", cursor.getString(0))
                                        put("priority", cursor.getInt(1))
                                    }
                                )
                            }
                        }
                }
        } finally {
            seedFile.delete()
        }
    }
}
