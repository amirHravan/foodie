package com.ravan.foodie.autoreserve.domain.repository

import com.ravan.foodie.autoreserve.api.AutoReserveApi
import com.ravan.foodie.autoreserve.db.dao.AutoReserveDao
import com.ravan.foodie.autoreserve.db.model.AutoReserveDaysEntity
import com.ravan.foodie.autoreserve.db.model.AutoReserveFoodEntity

class AutoReserveRepositoryImplementation(
    private val autoReserveDao: AutoReserveDao,
    private val autoReserveApi: AutoReserveApi,
): AutoReserveRepository{

    override suspend fun getAllFoodPriorities(): List<AutoReserveFoodEntity> {
        return autoReserveDao.getAllFoods()
    }

    override suspend fun updateFoodPriority(autoReserveFoodEntity: AutoReserveFoodEntity) {
        return autoReserveDao.updateFood(autoReserveFoodEntity)
    }

    override suspend fun getAllReserveDays(): AutoReserveDaysEntity? {
        return autoReserveDao.getAutoReserveDays()
    }

    override suspend fun updateReserveDays(autoReserveDaysEntity: AutoReserveDaysEntity) {
        return autoReserveDao.upsertReserveDays(autoReserveDaysEntity)
    }
}

//implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.8")