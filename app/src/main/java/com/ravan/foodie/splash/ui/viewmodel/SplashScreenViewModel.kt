package com.ravan.foodie.splash.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.model.PreferencesManager
import com.ravan.foodie.domain.network.ConnectionState
import com.ravan.foodie.domain.network.currentConnectivityState
import com.ravan.foodie.domain.ui.viewmodel.RavanViewModel
import com.ravan.foodie.domain.usecase.SaveSamadTokenUseCase
import com.ravan.foodie.domain.util.SharedPrefKeys
import com.ravan.foodie.profile.domain.usecase.CheckTokenValidationUseCase
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val checkTokenValidationUseCase: CheckTokenValidationUseCase,
    private val saveSamadTokenUseCase: SaveSamadTokenUseCase,
    private val preferencesManager: PreferencesManager,
) : RavanViewModel() {

    val username = mutableStateOf("")
    val password = mutableStateOf("")
    val showNetworkError = mutableStateOf(false)
    val navLogin: NavigationEvent = NavigationEvent()
    val navHome: NavigationEvent = NavigationEvent()

    init {
        preferencesManager.getData(SharedPrefKeys.Username.key, "").let {
            username.value = it
        }
        preferencesManager.getData(SharedPrefKeys.Password.key, "").let {
            password.value = it
        }
    }

    fun onStartSplash(context: Context) {
        when (context.currentConnectivityState) {
            ConnectionState.Available -> {
                checkTokenAvailability()
            }

            ConnectionState.Unavailable -> {
                showNetworkError.value = true
            }
        }
    }

    private fun checkTokenAvailability() {
        val token = preferencesManager.getData(SharedPrefKeys.LoginToken.key, "")
        if (token.isEmpty()) {
            navLogin.navigate()
            return
        }
        viewModelScope.launch {
            checkTokenValidationUseCase(token).fold(
                onSuccess = {
                    saveSamadTokenUseCase(token)
                    navHome.navigate()

                },
                onFailure = {
                    navLogin.navigate()
                }
            )
        }
    }

    fun onReload(context: Context) {
        showNetworkError.value = false
        onStartSplash(context = context)
    }
}
