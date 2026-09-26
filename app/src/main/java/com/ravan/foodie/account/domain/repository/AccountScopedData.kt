package com.ravan.foodie.account.domain.repository

/**
 * Local data kept separately for each account (food priorities, forget codes, ...).
 */
interface AccountScopedData {

    /**
     * Gives [toUsername] a copy of [fromUsername]'s data, or the app defaults when
     * [fromUsername] is null.
     */
    suspend fun copyAccountData(fromUsername: String?, toUsername: String)

    suspend fun deleteAccountData(username: String)
}
