package com.ravan.foodie.domain.exception

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class SamadApiErrorException(
    message: String = "درخواستمون زایید!",
) : HttpException(
    Response.error<Any>(
        400,
        ResponseBody.create("application/json".toMediaType(), message)
    )
) {

    override fun toString(): String {
        return "SamadApiErrorException(message=$message)"
    }
}