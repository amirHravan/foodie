package com.ravan.foodie.account

import com.ravan.foodie.account.domain.model.Account
import com.ravan.foodie.account.domain.repository.AccountStore
import com.ravan.foodie.domain.util.SharedPrefKeys
import com.ravan.foodie.testing.FakeKeyValueStore
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AccountStoreTest {

    private val json = Json { ignoreUnknownKeys = true }
    private var now = 1_000L
    private val storage = FakeKeyValueStore()

    private fun store() = AccountStore(storage, json) { now }

    private fun account(username: String) = Account(username = username, password = "pw-$username")

    @Test
    fun `migrates the single-account login into an active account`() {
        storage.values.putAll(
            mapOf(
                SharedPrefKeys.LegacyUsername.key to "400100100",
                SharedPrefKeys.LegacyPassword.key to "secret",
                SharedPrefKeys.LegacyAccessToken.key to "bearer a",
                SharedPrefKeys.LegacyRefreshToken.key to "r",
            )
        )

        val store = store()

        val active = store.active!!
        assertEquals("400100100", active.username)
        assertEquals("secret", active.password)
        assertEquals("bearer a", active.accessToken)
        assertEquals("r", active.refreshToken)
        assertEquals("400100100", store.legacyUsername)
        listOf(
            SharedPrefKeys.LegacyUsername,
            SharedPrefKeys.LegacyPassword,
            SharedPrefKeys.LegacyAccessToken,
            SharedPrefKeys.LegacyRefreshToken,
        ).forEach { assertNull(storage.values[it.key]) }
    }

    @Test
    fun `fresh install starts with no accounts and no legacy owner`() {
        val store = store()

        assertTrue(store.state.value.accounts.isEmpty())
        assertNull(store.active)
        assertNull(store.legacyUsername)
    }

    @Test
    fun `migration runs only once`() {
        storage.values[SharedPrefKeys.LegacyUsername.key] = "a"
        storage.values[SharedPrefKeys.LegacyPassword.key] = "p"
        store().remove("a")

        // A leftover legacy key from some other writer must not bring the account back.
        storage.values[SharedPrefKeys.LegacyUsername.key] = "a"
        storage.values[SharedPrefKeys.LegacyPassword.key] = "p"

        assertTrue(store().state.value.accounts.isEmpty())
    }

    @Test
    fun `state survives a restart`() {
        store().apply {
            upsert(account("a"))
            upsert(account("b"))
            setActive("b")
        }

        val reloaded = store()

        assertEquals(listOf("a", "b"), reloaded.state.value.accounts.map { it.username })
        assertEquals("b", reloaded.active?.username)
        assertEquals("pw-b", reloaded.active?.password)
    }

    @Test
    fun `upsert replaces an account with the same username in place`() {
        val store = store()
        store.upsert(account("a"))
        store.upsert(account("b"))

        store.upsert(account("a").copy(password = "new"))

        assertEquals(listOf("a", "b"), store.state.value.accounts.map { it.username })
        assertEquals("new", store.find("a")?.password)
    }

    @Test
    fun `setActive records when the account was last used`() {
        val store = store()
        store.upsert(account("a"))
        now = 5_000L

        store.setActive("a")

        assertEquals(5_000L, store.find("a")?.lastUsedAt)
    }

    @Test
    fun `removing the active account leaves no active account`() {
        val store = store()
        store.upsert(account("a"))
        store.setActive("a")

        store.remove("a")

        assertNull(store.active)
        assertNull(storage.values[SharedPrefKeys.ActiveAccount.key])
    }

    @Test
    fun `move reorders accounts and ignores invalid indices`() {
        val store = store()
        listOf("a", "b", "c").forEach { store.upsert(account(it)) }

        store.move(0, 2)
        assertEquals(listOf("b", "c", "a"), store.state.value.accounts.map { it.username })

        store.move(0, 5)
        assertEquals(listOf("b", "c", "a"), store.state.value.accounts.map { it.username })
    }

    @Test
    fun `unreadable saved data starts empty instead of crashing`() {
        storage.values[SharedPrefKeys.Accounts.key] = "not json"

        assertTrue(store().state.value.accounts.isEmpty())
    }

    @Test
    fun `toString never includes credentials`() {
        val text = Account(
            username = "a",
            password = "secret",
            accessToken = "access",
            refreshToken = "refresh",
        ).toString()

        assertFalse(text.contains("secret"))
        assertFalse(text.contains("access"))
        assertFalse(text.contains("refresh"))
    }
}
