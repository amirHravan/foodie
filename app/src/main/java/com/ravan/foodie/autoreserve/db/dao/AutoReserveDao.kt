package com.ravan.foodie.autoreserve.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ravan.foodie.autoreserve.db.dao.model.AutoReserveDaysDao
import com.ravan.foodie.autoreserve.db.dao.model.AutoReserveFoodDao

@Dao
interface AutoReserveDao {
    @Query("SELECT * FROM food_priority")
    suspend fun getAllFoods(): List<AutoReserveFoodDao>

    @Update
    suspend fun updateFood(food: AutoReserveFoodDao)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFoods(foods: List<AutoReserveFoodDao>)

    @Query("SELECT * FROM auto_reserve_days WHERE id = 0 LIMIT 1")
    suspend fun getAutoReserveDays(): AutoReserveDaysDao?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertReserveDays(autoReserveDays: AutoReserveDaysDao)

    @Query("DELETE FROM auto_reserve_days")
    suspend fun clearAutoReserveDays()
}