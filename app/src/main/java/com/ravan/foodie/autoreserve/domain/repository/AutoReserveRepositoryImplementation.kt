package com.ravan.foodie.autoreserve.domain.repository

import androidx.room.withTransaction
import com.ravan.foodie.autoreserve.db.AutoReserveDataBase
import com.ravan.foodie.autoreserve.db.model.AutoReserveDaysEntity
import com.ravan.foodie.autoreserve.db.model.AutoReserveFoodEntity
import com.ravan.foodie.autoreserve.db.model.TEMPLATE_ACCOUNT_ID

class AutoReserveRepositoryImplementation(
    private val database: AutoReserveDataBase,
    /** Username of the active account. */
    private val activeAccountId: () -> String?,
) : AutoReserveRepository {

    private val dao = database.foodDao()

    override suspend fun getAllFoodPriorities(): List<AutoReserveFoodEntity> {
        val accountId = requireAccountId()
        // Accounts saved before their data existed (e.g. the database was created after the
        // account) start from the defaults.
        if (dao.countFoods(accountId) == 0) {
            copyAccountData(fromUsername = null, toUsername = accountId)
        }
        return dao.getAllFoods(accountId)
    }

    override suspend fun updateFoodPriority(id: Int, priority: Int) {
        dao.updatePriority(id = id, priority = priority)
    }

    override suspend fun getAllReserveDays(): AutoReserveDaysEntity? {
        return dao.getAutoReserveDays(requireAccountId())
    }

    override suspend fun updateReserveDays(days: List<String>) {
        dao.upsertReserveDays(AutoReserveDaysEntity(accountId = requireAccountId(), days = days))
    }

    override suspend fun insertFood(name: String, priority: Int) {
        dao.insertFood(
            AutoReserveFoodEntity(accountId = requireAccountId(), name = name, priority = priority)
        )
    }

    override suspend fun copyAccountData(fromUsername: String?, toUsername: String) {
        database.withTransaction {
            val source = fromUsername
                ?.takeIf { dao.countFoods(it) > 0 }
                ?: TEMPLATE_ACCOUNT_ID
            dao.deleteFoods(toUsername)
            dao.insertAllFoods(
                dao.getAllFoods(source).map { it.copy(id = 0, accountId = toUsername) }
            )
            fromUsername?.let { dao.getAutoReserveDays(it) }?.let {
                dao.upsertReserveDays(it.copy(accountId = toUsername))
            }
        }
    }

    override suspend fun deleteAccountData(username: String) {
        database.withTransaction {
            dao.deleteFoods(username)
            dao.deleteReserveDays(username)
        }
    }

    private fun requireAccountId(): String =
        checkNotNull(activeAccountId()) { "No active account" }
}
