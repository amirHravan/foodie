package com.ravan.foodie.account.domain.model

data class AccountsState(
    /** In the order the user arranged them. */
    val accounts: List<Account> = emptyList(),
    val activeUsername: String? = null,
) {
    val active: Account?
        get() = accounts.firstOrNull { it.username == activeUsername }

    fun find(username: String): Account? = accounts.firstOrNull { it.username == username }
}
