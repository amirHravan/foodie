package com.ravan.foodie.login.ui.viewmodel

import android.util.Log
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
import com.ravan.foodie.domain.util.toEnglishNumber
import com.ravan.foodie.domain.util.toLocalNumber
import com.ravan.foodie.login.domain.model.SamadToken
import com.ravan.foodie.login.domain.usecase.SamadLoginUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val samadLoginUseCase: SamadLoginUseCase,
    private val preferencesManager: PreferencesManager,
) : FoodieViewModel() {

    val username = mutableStateOf("")
    val password = mutableStateOf("")
    val loginToken: MutableState<LoadableData<String>> = mutableStateOf(LoadableData.NotLoaded)
    val informationBoxData: MutableState<FoodieInformationBoxUIModel?> = mutableStateOf(null)

    val navHome: NavigationEvent = NavigationEvent()

    fun onLaunch() {
        preferencesManager.getString(SharedPrefKeys.Username.key, "").let {
            username.value = it.toLocalNumber()
        }
        preferencesManager.getString(SharedPrefKeys.Password.key, "").let {
            password.value = it.toLocalNumber()
        }
        Log.d("LoginScreenViewModel", "onLaunch: ${username.value}, ${password.value}")
        if (username.value.isEmpty() || password.value.isEmpty()) {
            return
        }
        onLoginClick()
    }

    fun onUserNameChange(newUserName: String) {
        username.value = newUserName.toLocalNumber()
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword.toLocalNumber()
    }

    fun onLoginClick() {
        loginToken.value = LoadableData.Loading
        viewModelScope.launch {
            samadLoginUseCase(username.value, password.value).fold(
                onSuccess = {
                    saveData(it)
                    loginToken.value = LoadableData.Loaded(it.accessToken)
                    navHome.navigate()
                },
                onFailure = {
                    Log.d("LoginScreenViewModel", "onLaunch: $it")
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
        preferencesManager.putString(SharedPrefKeys.Username.key, username.value.toEnglishNumber())
        preferencesManager.putString(SharedPrefKeys.Password.key, password.value.toEnglishNumber())
        preferencesManager.putString(SharedPrefKeys.AccessToken.key, token.accessToken)
        preferencesManager.putString(SharedPrefKeys.RefreshToken.key, token.refreshToken)
    }

    private fun showInformationBox(message: String, state: FoodieInformationBoxState) {
        viewModelScope.launch {
            informationBoxData.value = FoodieInformationBoxUIModel(state, message)
            delay(5000)
            informationBoxData.value = null
        }
    }

}