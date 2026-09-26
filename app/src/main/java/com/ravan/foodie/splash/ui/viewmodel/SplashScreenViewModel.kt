package com.ravan.foodie.splash.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.account.domain.model.AuthException
import com.ravan.foodie.account.domain.model.ResumeResult
import com.ravan.foodie.account.domain.repository.AccountManager
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.network.ConnectionState
import com.ravan.foodie.domain.network.currentConnectivityState
import com.ravan.foodie.domain.ui.viewmodel.FoodieViewModel
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val accountManager: AccountManager,
) : FoodieViewModel() {

    val showNetworkError = mutableStateOf(false)
    val navLogin: NavigationEvent = NavigationEvent()
    val navReauth: NavigationEvent = NavigationEvent()
    val navReserveInfo: NavigationEvent = NavigationEvent()

    /** Account whose saved password was rejected, for [navReauth]. */
    var reauthUsername: String? = null
        private set

    fun onLaunch(context: Context) {
        when (context.currentConnectivityState) {
            ConnectionState.Available -> resumeActiveAccount()
            ConnectionState.Unavailable -> showNetworkError.value = true
        }
    }

    private fun resumeActiveAccount() {
        viewModelScope.launch {
            when (val result = accountManager.resumeActive()) {
                ResumeResult.Resumed -> navReserveInfo.navigate()
                ResumeResult.NoAccount -> navLogin.navigate()
                is ResumeResult.NeedsLogin -> when (result.error) {
                    is AuthException.Network -> showNetworkError.value = true
                    is AuthException.InvalidCredentials -> {
                        reauthUsername = result.username
                        navReauth.navigate()
                    }
                }
            }
        }
    }

    fun onReload(context: Context) {
        showNetworkError.value = false
        onLaunch(context = context)
    }
}
