package com.ravan.foodie.reserveinfo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ravan.foodie.reserveinfo.db.dao.ForgetCodeDao
import com.ravan.foodie.reserveinfo.db.model.ForgetCodeEntity

@Database(entities = [ForgetCodeEntity::class], version = ForgetCodeDatabase.DATABASE_VERSION)
abstract class ForgetCodeDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "forget_code_database"
        const val DATABASE_VERSION = 1
        const val DATABASE_TABLE_NAME = "forget_code_table"
    }

    abstract fun forgetCodeDao(): ForgetCodeDao
}