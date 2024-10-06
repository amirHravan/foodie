package com.ravan.foodie.autoreserve.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ravan.foodie.autoreserve.db.converter.DaysConverter
import com.ravan.foodie.autoreserve.db.dao.AutoReserveDao
import com.ravan.foodie.autoreserve.db.model.AutoReserveDaysEntity
import com.ravan.foodie.autoreserve.db.model.AutoReserveFoodEntity

@Database(entities = [AutoReserveFoodEntity::class, AutoReserveDaysEntity::class], version = 1)
@TypeConverters(DaysConverter::class)
abstract class AutoReserveDataBase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "auto_reserve_db"
    }
    abstract fun foodDao(): AutoReserveDao
}