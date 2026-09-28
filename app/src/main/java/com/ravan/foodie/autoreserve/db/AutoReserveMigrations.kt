package com.ravan.foodie.autoreserve.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ravan.foodie.autoreserve.db.model.TEMPLATE_ACCOUNT_ID

/**
 * Version 2 keeps priorities and days per account. Existing rows move to the account that was
 * created from the single-account login ([legacyUsername]); a copy of the food list with default
 * priorities becomes the template for new accounts.
 */
class AutoReserveMigration1To2(
    private val legacyUsername: () -> String?,
) : Migration(1, 2) {

    override fun migrate(db: SupportSQLiteDatabase) {
        val owner = legacyUsername()?.takeIf { it.isNotEmpty() }

        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `food_priority_new` (" +
                "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "`accountId` TEXT NOT NULL, `name` TEXT NOT NULL, `priority` INTEGER NOT NULL)"
        )
        db.execSQL(
            "INSERT INTO `food_priority_new` (`accountId`, `name`, `priority`) " +
                "SELECT ?, `name`, 0 FROM `food_priority` ORDER BY `id`",
            arrayOf(TEMPLATE_ACCOUNT_ID)
        )
        if (owner != null) {
            db.execSQL(
                "INSERT INTO `food_priority_new` (`accountId`, `name`, `priority`) " +
                    "SELECT ?, `name`, `priority` FROM `food_priority` ORDER BY `id`",
                arrayOf(owner)
            )
        }
        db.execSQL("DROP TABLE `food_priority`")
        db.execSQL("ALTER TABLE `food_priority_new` RENAME TO `food_priority`")
        db.execSQL(
            "CREATE INDEX IF NOT EXISTS `index_food_priority_accountId` " +
                "ON `food_priority` (`accountId`)"
        )

        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `auto_reserve_days_new` (" +
                "`accountId` TEXT NOT NULL, `days` TEXT NOT NULL, PRIMARY KEY(`accountId`))"
        )
        if (owner != null) {
            db.execSQL(
                "INSERT INTO `auto_reserve_days_new` (`accountId`, `days`) " +
                    "SELECT ?, `days` FROM `auto_reserve_days` WHERE `id` = 0",
                arrayOf(owner)
            )
        }
        db.execSQL("DROP TABLE `auto_reserve_days`")
        db.execSQL("ALTER TABLE `auto_reserve_days_new` RENAME TO `auto_reserve_days`")
    }
}
