package com.ravan.foodie.account

import com.ravan.foodie.account.domain.model.AccountLimitException
import com.ravan.foodie.account.domain.model.AuthException
import com.ravan.foodie.account.domain.model.RemoveResult
import com.ravan.foodie.account.domain.model.ResumeResult
import com.ravan.foodie.account.domain.repository.AccountManager
import com.ravan.foodie.account.domain.repository.AccountScopedData
import com.ravan.foodie.account.domain.repository.AccountStore
import com.ravan.foodie.account.domain.repository.ReserveInFlightTracker
import com.ravan.foodie.domain.repository.TokenProvider
import com.ravan.foodie.testing.FakeKeyValueStore
import com.ravan.foodie.testing.FakeTokenApi
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AccountManagerTest {

    private var now = 0L
    private val store = AccountStore(FakeKeyValueStore(), Json) { ++now }
    private val api = FakeTokenApi(
        passwords = mutableMapOf("me" to "pw-me", "ali" to "pw-ali", "sara" to "pw-sara")
    )
    private val tokenProvider = TokenProvider(api, store)
    private val inFlight = ReserveInFlightTracker()
    private val accountData = FakeAccountData()
    private val names = mutableMapOf("me" to "Me Myself", "ali" to "Ali Rezaei")

    private fun TestScope.manager() = AccountManager(
        store = store,
        tokenProvider = tokenProvider,
        inFlight = inFlight,
        accountData = listOf(accountData),
        loadDisplayName = {
            names[store.active?.username]?.let { Result.success(it) }
                ?: Result.failure(Exception("no profile"))
        },
        appScope = this,
    )

    private suspend fun AccountManager.addAll(vararg usernames: String) {
        usernames.forEach { addAccount(it, "pw-$it").getOrThrow() }
    }

    @Test
    fun `adding an account logs in, activates it and loads its name`() = runTest {
        val manager = manager()

        val account = manager.addAccount("me", "pw-me").getOrThrow()
        advanceUntilIdle()

        assertEquals("me", store.active?.username)
        assertTrue(account.accessToken.startsWith("bearer me:access"))
        assertEquals("Me Myself", store.active?.displayName)
    }

    @Test
    fun `usernames typed with Persian digits are stored with English digits`() = runTest {
        api.passwords["400100100"] = "1234"

        manager().addAccount("۴۰۰۱۰۰۱۰۰", "۱۲۳۴").getOrThrow()

        assertEquals("400100100", store.active?.username)
    }

    @Test
    fun `a new account copies the active account's data`() = runTest {
        val manager = manager()
        manager.addAll("me")

        manager.addAccount("ali", "pw-ali").getOrThrow()

        assertEquals(listOf(null to "me", "me" to "ali"), accountData.copies)
    }

    @Test
    fun `wrong password adds nothing`() = runTest {
        val result = manager().addAccount("me", "wrong")

        assertTrue(result.exceptionOrNull() is AuthException.InvalidCredentials)
        assertTrue(store.state.value.accounts.isEmpty())
    }

    @Test
    fun `adding a saved username updates it instead of adding a duplicate`() = runTest {
        val manager = manager()
        manager.addAll("me", "ali")
        store.update("me") { it.copy(needsReauth = true) }
        api.passwords["me"] = "changed"

        manager.addAccount("me", "changed").getOrThrow()

        assertEquals(listOf("me", "ali"), store.state.value.accounts.map { it.username })
        assertEquals("changed", store.find("me")?.password)
        assertFalse(store.find("me")!!.needsReauth)
        assertEquals("me", store.active?.username)
        assertEquals(2, accountData.copies.size)
    }

    @Test
    fun `no more than five accounts`() = runTest {
        val manager = manager()
        (1..5).forEach { api.passwords["u$it"] = "pw-u$it" }
        manager.addAll("u1", "u2", "u3", "u4", "u5")
        api.loginCalls.clear()

        val result = manager.addAccount("me", "pw-me")

        assertTrue(result.exceptionOrNull() is AccountLimitException)
        assertTrue(api.loginCalls.isEmpty())
        // Re-adding a saved account is still allowed at the limit.
        assertTrue(manager.addAccount("u1", "pw-u1").isSuccess)
    }

    @Test
    fun `switching uses the refresh token and skips the password`() = runTest {
        val manager = manager()
        manager.addAll("me", "ali")
        api.loginCalls.clear()

        manager.switchTo("me").getOrThrow()

        assertEquals("me", store.active?.username)
        assertTrue(api.loginCalls.isEmpty())
        assertEquals(1, api.refreshCalls.size)
    }

    @Test
    fun `switching falls back to the saved password when the refresh token is dead`() =
        runTest {
            val manager = manager()
            manager.addAll("me", "ali")
            api.validRefreshTokens.clear()

            manager.switchTo("me").getOrThrow()

            assertEquals("me", store.active?.username)
            assertEquals(listOf("me"), api.loginCalls.takeLast(1))
        }

    @Test
    fun `a rejected password keeps the current account and marks the other one`() = runTest {
        val manager = manager()
        manager.addAll("me", "ali")
        api.validRefreshTokens.clear()
        api.passwords["me"] = "changed elsewhere"

        val result = manager.switchTo("me")

        assertTrue(result.exceptionOrNull() is AuthException.InvalidCredentials)
        assertEquals("ali", store.active?.username)
        assertTrue(store.find("me")!!.needsReauth)
    }

    @Test
    fun `a network error keeps the current account without marking the other one`() =
        runTest {
            val manager = manager()
            manager.addAll("me", "ali")
            api.networkDown = true

            val result = manager.switchTo("me")

            assertTrue(result.exceptionOrNull() is AuthException.Network)
            assertEquals("ali", store.active?.username)
            assertFalse(store.find("me")!!.needsReauth)
        }

    @Test
    fun `switching waits for running reservations`() = runTest {
        val manager = manager()
        manager.addAll("me", "ali")
        val reservation = CompletableDeferred<Unit>()
        launch { inFlight.track { reservation.await() } }
        runCurrent()

        val switch = async { manager.switchTo("me") }
        runCurrent()
        assertEquals("ali", store.active?.username)

        reservation.complete(Unit)
        switch.await().getOrThrow()
        assertEquals("me", store.active?.username)
    }

    @Test
    fun `a name that arrives after another switch is not applied`() = runTest {
        val nameGate = CompletableDeferred<Unit>()
        val manager = AccountManager(
            store = store,
            tokenProvider = tokenProvider,
            inFlight = inFlight,
            accountData = listOf(accountData),
            loadDisplayName = { nameGate.await(); Result.success("Stale Name") },
            appScope = this,
        )
        manager.addAll("me", "ali")
        manager.switchTo("me").getOrThrow()

        nameGate.complete(Unit)
        advanceUntilIdle()

        assertEquals("Stale Name", store.find("me")?.displayName)
        assertNull(store.find("ali")?.displayName)
    }

    @Test
    fun `removing another account keeps the active one`() = runTest {
        val manager = manager()
        manager.addAll("me", "ali")

        val result = manager.remove("me")

        assertEquals(RemoveResult.Removed, result)
        assertEquals("ali", store.active?.username)
        assertEquals(listOf("me"), accountData.deleted)
        assertNull(store.find("me"))
    }

    @Test
    fun `removing the active account switches to the most recently used one`() = runTest {
        val manager = manager()
        manager.addAll("me", "ali", "sara")
        manager.switchTo("ali").getOrThrow()
        manager.switchTo("sara").getOrThrow()

        val result = manager.remove("sara")

        assertEquals(RemoveResult.SwitchedTo("ali"), result)
        assertEquals("ali", store.active?.username)
    }

    @Test
    fun `removing the active account skips accounts that can't log in`() = runTest {
        val manager = manager()
        manager.addAll("me", "ali", "sara")
        manager.switchTo("ali").getOrThrow()
        manager.switchTo("sara").getOrThrow()
        api.validRefreshTokens.removeAll { it.startsWith("ali") }
        api.passwords["ali"] = "changed"

        val result = manager.remove("sara")

        assertEquals(RemoveResult.SwitchedTo("me"), result)
        assertTrue(store.find("ali")!!.needsReauth)
    }

    @Test
    fun `removing the last account asks for a new login`() = runTest {
        val manager = manager()
        manager.addAll("me")

        val result = manager.remove("me")

        assertEquals(RemoveResult.NeedsLogin(null), result)
        assertNull(store.active)
    }

    @Test
    fun `resume reports each outcome`() = runTest {
        val manager = manager()
        assertEquals(ResumeResult.NoAccount, manager.resumeActive())

        manager.addAll("me")
        assertEquals(ResumeResult.Resumed, manager.resumeActive())

        api.validRefreshTokens.clear()
        api.passwords["me"] = "changed"
        val result = manager.resumeActive()
        assertTrue(result is ResumeResult.NeedsLogin)
        assertTrue((result as ResumeResult.NeedsLogin).error is AuthException.InvalidCredentials)
    }

    @Test
    fun `a 401 refresh saves tokens to the request's account, not the active one`() = runTest {
        val manager = manager()
        manager.addAll("me", "ali")
        val meBefore = store.find("me")!!.accessToken

        // A request started as "me" gets a 401 after "ali" became active.
        tokenProvider.refreshAccessToken("me").getOrThrow()

        val me = store.find("me")!!
        assertTrue(me.accessToken != meBefore)
        assertEquals("ali", store.active?.username)
    }

    private class FakeAccountData : AccountScopedData {
        val copies = mutableListOf<Pair<String?, String>>()
        val deleted = mutableListOf<String>()

        override suspend fun copyAccountData(fromUsername: String?, toUsername: String) {
            copies += fromUsername to toUsername
        }

        override suspend fun deleteAccountData(username: String) {
            deleted += username
        }
    }
}
