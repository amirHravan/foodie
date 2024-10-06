package com.ravan.foodie.autoreserve.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ravan.foodie.autoreserve.db.model.AutoReserveDaysEntity
import com.ravan.foodie.autoreserve.db.model.AutoReserveFoodEntity

@Dao
interface AutoReserveDao {
    @Query("SELECT * FROM food_priority")
    suspend fun getAllFoods(): List<AutoReserveFoodEntity>

    @Update
    suspend fun updateFood(food: AutoReserveFoodEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFoods(foods: List<AutoReserveFoodEntity>)

    @Query("SELECT * FROM auto_reserve_days WHERE id = 0 LIMIT 1")
    suspend fun getAutoReserveDays(): AutoReserveDaysEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertReserveDays(autoReserveDays: AutoReserveDaysEntity)

    @Query("DELETE FROM auto_reserve_days")
    suspend fun clearAutoReserveDays()
}