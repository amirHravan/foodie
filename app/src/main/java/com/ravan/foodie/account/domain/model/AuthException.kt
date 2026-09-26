package com.ravan.foodie.account.domain.model

/**
 * Why logging in to Samad failed. Only [InvalidCredentials] means the stored password is wrong;
 * a [Network] failure says nothing about the account.
 */
sealed class AuthException(message: String) : Exception(message) {
    class InvalidCredentials(message: String) : AuthException(message)

    class Network(message: String) : AuthException(message)
}
