package com.ravan.foodie.autoreserve.domain.repository

import com.ravan.foodie.autoreserve.api.AutoReserveApi
import com.ravan.foodie.autoreserve.db.dao.AutoReserveDao
import com.ravan.foodie.autoreserve.db.dao.model.AutoReserveDaysDao
import com.ravan.foodie.autoreserve.db.dao.model.AutoReserveFoodDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AutoReserveRepositoryImplementation(
    private val autoReserveDao: AutoReserveDao,
    private val autoReserveApi: AutoReserveApi,
): AutoReserveRepository{

    init {
        CoroutineScope(Dispatchers.IO).launch{
            autoReserveDao.insertAllFoods(
                listOf(
                    AutoReserveFoodDao(1, "Bread", 1),
                    AutoReserveFoodDao(2, "Milk", 2),
                    AutoReserveFoodDao(3, "Egg", 3),
                    AutoReserveFoodDao(4, "Butter", 4),
                    AutoReserveFoodDao(5, "Cheese", 5),
                )
            )
        }
    }
    override suspend fun getAllFoodPriorities(): List<AutoReserveFoodDao> {
        return autoReserveDao.getAllFoods()
    }

    override suspend fun updateFoodPriority(autoReserveFoodDao: AutoReserveFoodDao) {
        return autoReserveDao.updateFood(autoReserveFoodDao)
    }

    override suspend fun getAllReserveDays(): AutoReserveDaysDao? {
        return autoReserveDao.getAutoReserveDays()
    }

    override suspend fun updateReserveDays(autoReserveDaysDao: AutoReserveDaysDao) {
        return autoReserveDao.upsertReserveDays(autoReserveDaysDao)
    }
}