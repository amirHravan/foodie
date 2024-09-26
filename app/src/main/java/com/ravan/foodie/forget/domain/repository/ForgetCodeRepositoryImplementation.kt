package com.ravan.foodie.forget.domain.repository

import android.util.Log
import com.ravan.foodie.forget.api.ForgetCodeApi
import com.ravan.foodie.forget.api.dto.toForgetCode
import com.ravan.foodie.forget.domain.model.ForgetCode

class ForgetCodeRepositoryImplementation(
    private val api: ForgetCodeApi,
) : ForgetCodeRepository {

    private val forgetCodeCache = mutableMapOf<Int, String>()

    override suspend fun getForgetCode(
        reserveId: Int
    ): Result<ForgetCode> {
        val forgetCode = forgetCodeCache[reserveId]?.let {
            Result.success(ForgetCode(it))
        } ?: run {
            try {
                val result = api.getForgetCode(reserveId)
                if (result.isSuccessful) {
                    Result.success(result.payload!!.toForgetCode())
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


}