package com.ravan.foodie.reserveinfo.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Version 2 records which account each forget code belongs to, so removing an account removes its
 * codes. Existing codes belong to the account created from the single-account login.
 */
class ForgetCodeMigration1To2(
    private val legacyUsername: () -> String?,
) : Migration(1, 2) {

    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "ALTER TABLE `forget_code_table` ADD COLUMN `accountId` TEXT NOT NULL DEFAULT ''"
        )
        legacyUsername()?.takeIf { it.isNotEmpty() }?.let {
            db.execSQL("UPDATE `forget_code_table` SET `accountId` = ?", arrayOf(it))
        }
    }
}
