package com.ravan.foodie.account.domain.repository

import com.ravan.foodie.account.domain.model.Account
import com.ravan.foodie.account.domain.model.AccountsState
import com.ravan.foodie.domain.model.KeyValueStore
import com.ravan.foodie.domain.util.SharedPrefKeys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

/**
 * Saved accounts and which one is active, persisted on every change.
 *
 * On first use after upgrading from a single-account version, the old username, password and
 * tokens are moved into an account and the old keys are removed.
 */
class AccountStore(
    private val storage: KeyValueStore,
    private val json: Json,
    private val clock: () -> Long = System::currentTimeMillis,
) {
    private val serializer = ListSerializer(Account.serializer())

    private val _state = MutableStateFlow(load())
    val state: StateFlow<AccountsState> = _state.asStateFlow()

    val active: Account?
        get() = _state.value.active

    /**
     * The account created from single-account data when upgrading, if any. Local databases use
     * it to decide who owns their existing rows.
     */
    val legacyUsername: String?
        get() = storage.getString(SharedPrefKeys.LegacyMigratedAccount.key)

    fun find(username: String): Account? = _state.value.find(username)

    /** Replaces the account with the same username, or appends it. */
    fun upsert(account: Account) = edit { state ->
        val index = state.accounts.indexOfFirst { it.username == account.username }
        val accounts = if (index == -1) {
            state.accounts + account
        } else {
            state.accounts.toMutableList().also { it[index] = account }
        }
        state.copy(accounts = accounts)
    }

    fun update(username: String, transform: (Account) -> Account) = edit { state ->
        state.copy(accounts = state.accounts.map {
            if (it.username == username) transform(it) else it
        })
    }

    fun setActive(username: String?) = edit { state ->
        require(username == null || state.find(username) != null) { "Unknown account" }
        state.copy(
            activeUsername = username,
            accounts = state.accounts.map {
                if (it.username == username) it.copy(lastUsedAt = clock()) else it
            },
        )
    }

    fun remove(username: String) = edit { state ->
        state.copy(
            accounts = state.accounts.filterNot { it.username == username },
            activeUsername = state.activeUsername.takeUnless { it == username },
        )
    }

    fun move(fromIndex: Int, toIndex: Int) = edit { state ->
        val accounts = state.accounts.toMutableList()
        if (fromIndex !in accounts.indices || toIndex !in accounts.indices) return@edit state
        accounts.add(toIndex, accounts.removeAt(fromIndex))
        state.copy(accounts = accounts)
    }

    // Tokens are refreshed on OkHttp threads while the UI edits accounts on the main thread;
    // updating and saving together keeps an older state from being written after a newer one.
    @Synchronized
    private fun edit(transform: (AccountsState) -> AccountsState) {
        _state.value = transform(_state.value)
        save(_state.value)
    }

    private fun save(state: AccountsState) {
        storage.putString(
            SharedPrefKeys.Accounts.key,
            json.encodeToString(serializer, state.accounts),
        )
        val active = state.activeUsername
        if (active == null) {
            storage.remove(SharedPrefKeys.ActiveAccount.key)
        } else {
            storage.putString(SharedPrefKeys.ActiveAccount.key, active)
        }
    }

    private fun load(): AccountsState {
        val stored = storage.getString(SharedPrefKeys.Accounts.key)
            ?: return migrateLegacyAccount()
        val accounts = try {
            json.decodeFromString(serializer, stored)
        } catch (e: SerializationException) {
            emptyList()
        }
        val active = storage.getString(SharedPrefKeys.ActiveAccount.key)
            ?.takeIf { username -> accounts.any { it.username == username } }
        return AccountsState(accounts = accounts, activeUsername = active)
    }

    private fun migrateLegacyAccount(): AccountsState {
        val username = storage.getString(SharedPrefKeys.LegacyUsername.key).orEmpty()
        val password = storage.getString(SharedPrefKeys.LegacyPassword.key).orEmpty()
        val state = if (username.isNotEmpty() && password.isNotEmpty()) {
            AccountsState(
                accounts = listOf(
                    Account(
                        username = username,
                        password = password,
                        accessToken = storage.getString(SharedPrefKeys.LegacyAccessToken.key)
                            .orEmpty(),
                        refreshToken = storage.getString(SharedPrefKeys.LegacyRefreshToken.key)
                            .orEmpty(),
                        lastUsedAt = clock(),
                    )
                ),
                activeUsername = username,
            )
        } else {
            AccountsState()
        }
        // Save first so a crash between the two steps can't lose the account.
        save(state)
        state.activeUsername?.let {
            storage.putString(SharedPrefKeys.LegacyMigratedAccount.key, it)
        }
        listOf(
            SharedPrefKeys.LegacyUsername,
            SharedPrefKeys.LegacyPassword,
            SharedPrefKeys.LegacyAccessToken,
            SharedPrefKeys.LegacyRefreshToken,
        ).forEach { storage.remove(it.key) }
        return state
    }
}
