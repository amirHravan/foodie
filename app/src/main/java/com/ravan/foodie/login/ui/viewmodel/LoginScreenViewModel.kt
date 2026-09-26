package com.ravan.foodie.login.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.account.domain.repository.AccountManager
import com.ravan.foodie.account.domain.repository.AccountStore
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxState
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxUIModel
import com.ravan.foodie.domain.ui.viewmodel.FoodieViewModel
import com.ravan.foodie.domain.util.toLocalNumber
import com.ravan.foodie.login.domain.model.LoginMode
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val accountManager: AccountManager,
    private val accountStore: AccountStore,
    val mode: LoginMode,
    private val reauthUsername: String?,
) : FoodieViewModel() {

    val username = mutableStateOf(reauthUsername?.toLocalNumber().orEmpty())
    val password = mutableStateOf("")
    val loginToken: MutableState<LoadableData<Unit>> = mutableStateOf(LoadableData.NotLoaded)
    val informationBoxData: MutableState<FoodieInformationBoxUIModel?> = mutableStateOf(null)
    private var hideInformationBoxJob: Job? = null

    /** The username can't be changed when logging in again to a saved account. */
    val isUsernameEditable: Boolean
        get() = mode != LoginMode.Reauth

    val navHome: NavigationEvent = NavigationEvent()

    fun onLaunch() {
        if (mode == LoginMode.Reauth && reauthUsername != null) {
            accountStore.find(reauthUsername)?.let {
                informationBoxData.value = FoodieInformationBoxUIModel(
                    FoodieInformationBoxState.FAILED,
                    "${it.label}: رمز عبور ذخیره‌شده دیگه کار نمی‌کنه.",
                )
            }
        }
    }

    fun onUserNameChange(newUserName: String) {
        if (isUsernameEditable) username.value = newUserName.toLocalNumber()
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword.toLocalNumber()
    }

    fun onLoginClick() {
        if (loginToken.value is LoadableData.Loading) return
        loginToken.value = LoadableData.Loading
        viewModelScope.launch {
            accountManager.addAccount(username.value, password.value).fold(
                onSuccess = {
                    loginToken.value = LoadableData.Loaded(Unit)
                    navHome.navigate()
                },
                onFailure = {
                    loginToken.value = LoadableData.Failed(it.message ?: "خطای ناشناخته")
                    showInformationBox(
                        message = it.message ?: "خطای ناشناخته",
                        state = FoodieInformationBoxState.FAILED
                    )
                }
            )
        }
    }

    private fun showInformationBox(message: String, state: FoodieInformationBoxState) {
        hideInformationBoxJob?.cancel()
        hideInformationBoxJob = viewModelScope.launch {
            informationBoxData.value = FoodieInformationBoxUIModel(state, message)
            delay(5000)
            informationBoxData.value = null
        }
    }
}
