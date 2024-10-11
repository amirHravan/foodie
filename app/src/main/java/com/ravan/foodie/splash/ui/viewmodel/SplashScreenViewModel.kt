package com.ravan.foodie.splash.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.model.PreferencesManager
import com.ravan.foodie.domain.network.ConnectionState
import com.ravan.foodie.domain.network.currentConnectivityState
import com.ravan.foodie.domain.ui.viewmodel.FoodieViewModel
import com.ravan.foodie.domain.usecase.CacheAccessTokenUseCase
import com.ravan.foodie.domain.usecase.LoginUseCase
import com.ravan.foodie.domain.usecase.RefreshAccessTokenUseCase
import com.ravan.foodie.domain.util.SharedPrefKeys
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val cacheAccessTokenUseCase: CacheAccessTokenUseCase,
    private val samadLoginUseCase: LoginUseCase,
    private val refreshAccessTokenUseCase: RefreshAccessTokenUseCase,
    private val preferencesManager: PreferencesManager,
) : FoodieViewModel() {

    private var username = ""
    private var password = ""

    private var refreshToken = ""

    val showNetworkError = mutableStateOf(false)
    val navLogin: NavigationEvent = NavigationEvent()
    val navReserveInfo: NavigationEvent = NavigationEvent()

    init {
        preferencesManager.getString(SharedPrefKeys.RefreshToken.key, "").let {
            refreshToken = it
        }


    }

    private fun loadUserNamePassword() {
        preferencesManager.getString(SharedPrefKeys.Username.key, "").let {
            username = it
        }

        preferencesManager.getString(SharedPrefKeys.Password.key, "").let {
            password = it
        }
    }

    fun onLaunch(context: Context) {
        when (context.currentConnectivityState) {
            ConnectionState.Available -> {
                checkOldAccessToken()
            }

            ConnectionState.Unavailable -> {
                showNetworkError.value = true
            }
        }
    }

    private fun checkOldAccessToken() {
        if (refreshToken.isNotEmpty()) {
            viewModelScope.launch {
                refreshAccessTokenUseCase().fold(
                    onSuccess = { _ ->
                        navReserveInfo.navigate()
                    },
                    onFailure = {
                        getNewAccessToken()
                    }
                )
            }
        } else {
            getNewAccessToken()
        }
    }

    private fun getNewAccessToken() {
        loadUserNamePassword()
        if (username.isNotEmpty() && password.isNotEmpty()) {
            viewModelScope.launch {
                samadLoginUseCase(username = username, password = password).fold(
                    onSuccess = { samadToken ->
                        cacheAccessTokenUseCase(samadToken)
                        navReserveInfo.navigate()
                    },
                    onFailure = {
                        navLogin.navigate()
                    }
                )
            }
        } else {
            navLogin.navigate()
        }
    }

    fun onReload(context: Context) {
        showNetworkError.value = false
        onLaunch(context = context)
    }
}
