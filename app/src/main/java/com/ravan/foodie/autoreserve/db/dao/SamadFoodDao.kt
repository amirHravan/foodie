package com.ravan.foodie.autoreserve.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ravan.foodie.autoreserve.domain.model.SamadFood

@Dao
interface SamadFoodDao {
    @Query("SELECT * FROM samad_foods")
    suspend fun getAllFoods(): List<SamadFood>

    @Update
    suspend fun updateFood(food: SamadFood)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(foods: List<SamadFood>)
}