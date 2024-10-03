package com.ravan.foodie.credit.domain.repository

import android.util.Log
import com.ravan.foodie.credit.api.CreditApi
import com.ravan.foodie.credit.api.dto.toRedirectLoginAsToken
import com.ravan.foodie.credit.domain.model.RedirectLoginAsToken

class CreditRepositoryImplementation(
    private val creditApi: CreditApi
) : CreditRepository {

    override suspend fun getLoginAsToken(): Result<RedirectLoginAsToken> {
        return try {
            val response = creditApi.getRedirectLoginAsToken()
            if (response.isSuccessful) {
                Result.success(response.payload!!.toRedirectLoginAsToken())
            } else {
                Result.failure(Exception(response.messageFa ?: "در ارتباط با سرور خطایی پیش آمده"))
            }
        } catch (e: Exception) {
            Log.e("CreditRepository:getLoginAsToken", "Error: $e")
            Result.failure(Throwable("در ارتباط با سرور خطایی پیش آمده"))
        }
    }

}