package com.ravan.foodie.account.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.account.domain.model.AuthException
import com.ravan.foodie.account.domain.model.RemoveResult
import com.ravan.foodie.account.domain.repository.AccountManager
import com.ravan.foodie.account.domain.repository.AccountStore
import com.ravan.foodie.account.domain.repository.ReserveInFlightTracker
import com.ravan.foodie.account.ui.model.AccountNavEvent
import com.ravan.foodie.account.ui.model.AccountRowUIModel
import com.ravan.foodie.account.ui.model.toAccountRowUIModel
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxState
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxUIModel
import com.ravan.foodie.domain.ui.viewmodel.FoodieViewModel
import com.ravan.foodie.login.domain.model.LoginMode
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Account list, switching, adding and removing. Lives at activity level so the Profile screen
 * and the long-press switcher share one state.
 */
class AccountsViewModel(
    private val accountManager: AccountManager,
    private val accountStore: AccountStore,
    inFlightTracker: ReserveInFlightTracker,
) : FoodieViewModel() {

    val accounts = mutableStateOf<ImmutableList<AccountRowUIModel>>(persistentListOf())
    val canAddAccount = mutableStateOf(true)
    val showSwitcher = mutableStateOf(false)

    /** Account waiting for the user to confirm its removal. */
    val pendingRemoval = mutableStateOf<AccountRowUIModel?>(null)

    /** Account being switched to; other switches are ignored meanwhile. */
    val switchingTo = mutableStateOf<AccountRowUIModel?>(null)

    /** Number of reserve/cancel requests a switch has to wait for. */
    val reservesInFlight: StateFlow<Int> = inFlightTracker.inFlight

    val status = mutableStateOf<FoodieInformationBoxUIModel?>(null)
    private var hideStatusJob: Job? = null

    private val _navEvents = Channel<AccountNavEvent>(Channel.BUFFERED)
    val navEvents: Flow<AccountNavEvent> = _navEvents.receiveAsFlow()

    /**
     * Owns the screens' view models. They live across tab changes as before, but belong to one
     * account: when the active account changes this store is cleared and replaced, so no screen
     * keeps the previous account's state (selected self, loaded program, ...).
     */
    val screenViewModelStoreOwner = mutableStateOf(newScreenStoreOwner())
    private var screenStoreAccount: String? = accountStore.active?.username

    init {
        viewModelScope.launch {
            accountStore.state.collect { state ->
                accounts.value = state.accounts
                    .map { it.toAccountRowUIModel(state.activeUsername) }
                    .toImmutableList()
                canAddAccount.value = state.accounts.size < AccountManager.MAX_ACCOUNTS
                if (state.activeUsername != screenStoreAccount) {
                    screenStoreAccount = state.activeUsername
                    val previous = screenViewModelStoreOwner.value
                    screenViewModelStoreOwner.value = newScreenStoreOwner()
                    previous.viewModelStore.clear()
                }
            }
        }
    }

    override fun onCleared() {
        screenViewModelStoreOwner.value.viewModelStore.clear()
    }

    fun onProfileLongPress() {
        showSwitcher.value = true
    }

    fun onSwitcherDismiss() {
        showSwitcher.value = false
    }

    fun onAccountClick(username: String) {
        if (switchingTo.value != null) return
        val row = accounts.value.firstOrNull { it.username == username } ?: return
        if (row.isActive) {
            showSwitcher.value = false
            return
        }
        if (row.needsReauth) {
            onLoginAgainClick(username)
            return
        }
        switchingTo.value = row
        viewModelScope.launch {
            accountManager.switchTo(username).fold(
                onSuccess = {
                    showSwitcher.value = false
                    _navEvents.send(AccountNavEvent.Home)
                },
                onFailure = {
                    showStatus(
                        when (it) {
                            is AuthException.InvalidCredentials ->
                                "${row.title}: رمز عبور ذخیره‌شده دیگه کار نمی‌کنه. «ورود دوباره» رو بزن."

                            else -> it.message ?: "عوض کردن حساب انجام نشد."
                        }
                    )
                }
            )
            switchingTo.value = null
        }
    }

    fun onAddAccountClick() {
        showSwitcher.value = false
        _navEvents.trySend(AccountNavEvent.Login(LoginMode.Add))
    }

    fun onLoginAgainClick(username: String) {
        showSwitcher.value = false
        _navEvents.trySend(AccountNavEvent.Login(LoginMode.Reauth, username))
    }

    fun onRemoveClick(username: String) {
        pendingRemoval.value = accounts.value.firstOrNull { it.username == username }
    }

    fun onRemoveActiveClick() {
        pendingRemoval.value = accounts.value.firstOrNull { it.isActive }
    }

    fun onRemoveDismiss() {
        pendingRemoval.value = null
    }

    fun onRemoveConfirm() {
        val username = pendingRemoval.value?.username ?: return
        pendingRemoval.value = null
        viewModelScope.launch {
            when (val result = accountManager.remove(username)) {
                RemoveResult.Removed -> Unit
                is RemoveResult.SwitchedTo -> _navEvents.send(AccountNavEvent.Home)
                is RemoveResult.NeedsLogin -> _navEvents.send(
                    AccountNavEvent.Login(
                        mode = if (result.username == null) LoginMode.Initial else LoginMode.Reauth,
                        username = result.username,
                        clearStack = true,
                    )
                )
            }
        }
    }

    fun onMove(fromIndex: Int, toIndex: Int) {
        accountManager.move(fromIndex, toIndex)
    }

    private fun showStatus(message: String) {
        hideStatusJob?.cancel()
        status.value = FoodieInformationBoxUIModel(FoodieInformationBoxState.FAILED, message)
        hideStatusJob = viewModelScope.launch {
            delay(STATUS_DURATION_MS)
            status.value = null
        }
    }

    private fun newScreenStoreOwner(): ViewModelStoreOwner = object : ViewModelStoreOwner {
        override val viewModelStore = ViewModelStore()
    }

    private companion object {
        const val STATUS_DURATION_MS = 5000L
    }
}
