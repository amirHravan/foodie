package com.ravan.foodie.splash.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.model.PreferencesManager
import com.ravan.foodie.domain.network.ConnectionState
import com.ravan.foodie.domain.network.currentConnectivityState
import com.ravan.foodie.domain.ui.viewmodel.FoodieViewModel
import com.ravan.foodie.domain.usecase.CheckTokenValidationUseCase
import com.ravan.foodie.domain.usecase.SaveSamadTokenUseCase
import com.ravan.foodie.domain.util.SharedPrefKeys
import com.ravan.foodie.login.domain.usecase.SamadLoginUseCase
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val saveSamadTokenUseCase: SaveSamadTokenUseCase,
    private val samadLoginUseCase: SamadLoginUseCase,
    preferencesManager: PreferencesManager,
) : FoodieViewModel() {

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

    fun onLaunch(context: Context) {
        when (context.currentConnectivityState) {
            ConnectionState.Available -> {
                getAccessToken()
            }

            ConnectionState.Unavailable -> {
                showNetworkError.value = true
            }
        }
    }

    private fun getAccessToken() {
        if (username.value.isNotEmpty() && password.value.isNotEmpty()) {
            viewModelScope.launch {
                samadLoginUseCase(username = username.value, password = password.value).fold(
                    onSuccess = { samadToken ->
                        saveSamadTokenUseCase(samadToken)
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
