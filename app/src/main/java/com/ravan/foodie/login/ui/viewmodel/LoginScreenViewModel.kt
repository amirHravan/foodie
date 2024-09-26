package com.ravan.foodie.login.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.model.PreferencesManager
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxState
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxUIModel
import com.ravan.foodie.domain.ui.viewmodel.FoodieViewModel
import com.ravan.foodie.domain.util.SharedPrefKeys
import com.ravan.foodie.login.domain.model.SamadToken
import com.ravan.foodie.login.domain.usecase.SamadLoginUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    val samadLoginUseCase: SamadLoginUseCase,
    private val preferencesManager: PreferencesManager,
) : FoodieViewModel() {

    val username = mutableStateOf("")
    val password = mutableStateOf("")
    val loginToken: MutableState<LoadableData> = mutableStateOf(LoadableData.NotLoaded)
    val informationBoxData: MutableState<FoodieInformationBoxUIModel?> = mutableStateOf(null)

    val navHome: NavigationEvent = NavigationEvent()

    fun onLaunch() {
        preferencesManager.getData(SharedPrefKeys.Username.key, "").let {
            username.value = it
        }
        preferencesManager.getData(SharedPrefKeys.Password.key, "").let {
            password.value = it
        }
        if (username.value.isEmpty() || password.value.isEmpty()) {
            return
        }
        viewModelScope.launch {
            samadLoginUseCase(username = username.value, password = password.value).fold(
                onSuccess = { samadToken ->
                    saveData(samadToken)
                    loginToken.value = LoadableData.Loaded(samadToken.fullToken)
                    navHome.navigate()
                },
                onFailure = {

                }
            )

        }


    }

    fun onUserNameChange(newUserName: String) {
        username.value = newUserName
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    fun onLoginClick() {
        viewModelScope.launch {
            samadLoginUseCase(username.value, password.value).fold(
                onSuccess = {
                    saveData(it)
                    loginToken.value = LoadableData.Loaded(it.fullToken)
                    navHome.navigate()
                },
                onFailure = {
                    loginToken.value = LoadableData.Failed("رمزعبور/نام‌کاربری اشتباه است.")
                    showInformationBox(
                        message = "رمزعبور/نام‌کاربری اشتباه است.",
                        state = FoodieInformationBoxState.FAILED
                    )
                }
            )
        }
    }

    private fun saveData(token: SamadToken) {
        preferencesManager.saveData(SharedPrefKeys.Username.key, username.value)
        preferencesManager.saveData(SharedPrefKeys.Password.key, password.value)
        preferencesManager.saveData(
            SharedPrefKeys.LoginToken.key,
            "${token.tokenType} ${token.accessToken}"
        )
        preferencesManager.saveData(
            SharedPrefKeys.RefreshToken.key,
            token.refreshToken
        )
    }

    private fun showInformationBox(message: String, state: FoodieInformationBoxState) {
        viewModelScope.launch {
            informationBoxData.value = FoodieInformationBoxUIModel(state, message)
            delay(5000)
            informationBoxData.value = null
        }
    }

}