package com.ravan.foodie.reserveinfo.domain.repository

import android.util.Log
import com.ravan.foodie.reserveinfo.api.ReserveInfoApi
import com.ravan.foodie.reserveinfo.api.dto.toForgetCode
import com.ravan.foodie.reserveinfo.api.dto.toReservationInfo
import com.ravan.foodie.reserveinfo.db.dao.ForgetCodeDao
import com.ravan.foodie.reserveinfo.domain.model.ForgetCode
import com.ravan.foodie.reserveinfo.domain.model.ReservationInfo
import com.ravan.foodie.reserveinfo.domain.model.toForgetCodeEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReservationInfoRepositoryImplementation(
    private val api: ReserveInfoApi,
    private val forgetCodeDao: ForgetCodeDao,
) : ReservationInfoRepository {

    private val forgetCodeCache = mutableMapOf<Int, String>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            cleanForgetCodeDataBase()
            forgetCodeDao.getAllForgetCodes().forEach {
                forgetCodeCache[it.reserveId] = it.code
            }
        }
    }

    private suspend fun cleanForgetCodeDataBase() {
        val twoWeeksAgo = System.currentTimeMillis() - (14 * 24 * 60 * 60 * 1000L)
        forgetCodeDao.deleteOldEntries(twoWeeksAgo)
        forgetCodeDao.deleteNonValidEntries()
    }


    override suspend fun getReservationInfo(weekStartDate: String): Result<ReservationInfo> {
        return try {
            val result = api.getReserveInformation(weekStartDate)
            if (result.isSuccessful) {
                Result.success(result.payload!!.toReservationInfo())
            } else {
                Result.failure(Exception(result.getErrorMessage()))
            }
        } catch (e: Exception) {
            Log.e("ReservationInfoRepository", "Error ${e.message}")
            Result.failure(Throwable("در ارتباط با سرور به مشکل خوردیم."))
        }
    }

    override suspend fun getForgetCode(
        reserveId: Int,
        dailySale: Boolean,
    ): Result<ForgetCode> {
        val forgetCode = forgetCodeCache[reserveId]?.let {
            Result.success(ForgetCode(reserveId = reserveId, code = it, isValid = true))
        } ?: run {
            try {
                val result = api.getForgetCode(reserveId = reserveId, dailySale = dailySale)
                if (result.isSuccessful) {
                    val forgetCode = result.payload!!.toForgetCode(reserveId)
                    forgetCodeCache[reserveId] = forgetCode.code
                    forgetCodeDao.insertForgetCode(forgetCode.toForgetCodeEntity())
                    Result.success(forgetCode)
                } else {
                    Result.failure(Exception(result.getErrorMessage()))
                }
            } catch (e: Exception) {
                Log.e("ForgetCodeRepository", "Error: $e")
                Result.failure(Throwable("در ارتباط با سرور به مشکل خوردیم."))
            }
        }

        return forgetCode
    }

    override fun invalidateCache() {
        forgetCodeCache.clear()
    }

    override fun getMapCache(): Map<Int, String> {
        return forgetCodeCache
    }

}