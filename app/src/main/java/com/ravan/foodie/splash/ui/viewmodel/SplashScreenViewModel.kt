package com.ravan.foodie.splash.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.model.PreferencesManager
import com.ravan.foodie.domain.network.ConnectionState
import com.ravan.foodie.domain.network.currentConnectivityState
import com.ravan.foodie.domain.ui.viewmodel.FoodieViewModel
import com.ravan.foodie.domain.usecase.CacheAccessTokenUseCase
import com.ravan.foodie.domain.usecase.CheckTokenValidationUseCase
import com.ravan.foodie.domain.util.SharedPrefKeys
import com.ravan.foodie.login.domain.model.SamadToken
import com.ravan.foodie.login.domain.usecase.SamadLoginUseCase
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val cacheAccessTokenUseCase: CacheAccessTokenUseCase,
    private val samadLoginUseCase: SamadLoginUseCase,
    private val checkTokenValidationUseCase: CheckTokenValidationUseCase,
    private val preferencesManager: PreferencesManager,
) : FoodieViewModel() {

    private var username = ""
    private var password = ""

    private var accessToken = ""
    private var refreshToken = ""

    val showNetworkError = mutableStateOf(false)
    val navLogin: NavigationEvent = NavigationEvent()
    val navHome: NavigationEvent = NavigationEvent()

    init {
        preferencesManager.getString(SharedPrefKeys.AccessToken.key, "").let {
            accessToken = it
        }

        preferencesManager.getString(SharedPrefKeys.RefreshToken.key, "").let {
            refreshToken = it
        }

        Log.d("SplashScreenViewModel", "accessToken: $accessToken, refreshToken: $refreshToken")

    }

    private fun loadUserNamePassword() {
        preferencesManager.getString(SharedPrefKeys.Username.key, "").let {
            username = it
        }

        preferencesManager.getString(SharedPrefKeys.Password.key, "").let {
            password = it
        }

        Log.d("SplashScreenViewModel", "loadUserNamePassword: $username, $password")

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
        if (accessToken.isNotEmpty()) {
            viewModelScope.launch {
                checkTokenValidationUseCase().fold(
                    onSuccess = {
                        cacheAccessTokenUseCase(
                            token = SamadToken(
                                accessToken = accessToken,
                                refreshToken = refreshToken,
                            )
                        )
                        navHome.navigate()
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
                        navHome.navigate()
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
