package com.ravan.foodie.autoreserve.db

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


class PrepopulateFoodsDatabase : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)


    }
}
