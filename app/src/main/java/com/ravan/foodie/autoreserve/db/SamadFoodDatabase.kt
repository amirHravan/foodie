package com.ravan.foodie.autoreserve.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ravan.foodie.autoreserve.db.dao.SamadFoodDao
import com.ravan.foodie.autoreserve.domain.model.SamadFood

@Database(entities = [SamadFood::class], version = 1)
abstract class SamadFoodDatabase : RoomDatabase() {
    abstract fun foodDao(): SamadFoodDao
}