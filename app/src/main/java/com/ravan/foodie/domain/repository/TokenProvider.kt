package com.ravan.foodie.domain.repository

import com.ravan.foodie.login.domain.model.SamadToken

class TokenProvider() {
    var token: SamadToken? = null

    fun getSamadToken(): SamadToken? {
        return token
    }

    fun setSamadToken(token: SamadToken) {
        this.token = token
    }
}

