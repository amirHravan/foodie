package com.ravan.foodie.autoreserve.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ravan.foodie.autoreserve.db.model.AutoReserveDaysEntity
import com.ravan.foodie.autoreserve.db.model.AutoReserveFoodEntity

@Dao
interface AutoReserveDao {
    @Query("SELECT * FROM food_priority WHERE accountId = :accountId ORDER BY id")
    suspend fun getAllFoods(accountId: String): List<AutoReserveFoodEntity>

    @Query("SELECT COUNT(*) FROM food_priority WHERE accountId = :accountId")
    suspend fun countFoods(accountId: String): Int

    @Query("UPDATE food_priority SET priority = :priority WHERE id = :id")
    suspend fun updatePriority(id: Int, priority: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFoods(foods: List<AutoReserveFoodEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: AutoReserveFoodEntity)

    @Query("DELETE FROM food_priority WHERE accountId = :accountId")
    suspend fun deleteFoods(accountId: String)

    @Query("SELECT * FROM auto_reserve_days WHERE accountId = :accountId LIMIT 1")
    suspend fun getAutoReserveDays(accountId: String): AutoReserveDaysEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertReserveDays(autoReserveDays: AutoReserveDaysEntity)

    @Query("DELETE FROM auto_reserve_days WHERE accountId = :accountId")
    suspend fun deleteReserveDays(accountId: String)
}
