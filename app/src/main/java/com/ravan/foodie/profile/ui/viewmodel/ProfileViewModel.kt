package com.ravan.foodie.profile.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.account.domain.repository.AccountManager
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.ui.viewmodel.FoodieViewModel
import com.ravan.foodie.profile.domain.usecase.GetNurtureProfile
import com.ravan.foodie.profile.ui.model.ProfileScreenUIModel
import com.ravan.foodie.profile.ui.model.toProfileScreenUIModel
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getNurtureProfile: GetNurtureProfile,
    private val accountManager: AccountManager,
) : FoodieViewModel() {

    val profileScreenUIModel =
        mutableStateOf<LoadableData<ProfileScreenUIModel>>(LoadableData.NotLoaded)
    val navBack = NavigationEvent()

    fun onLaunch() {
        loadProfile()
    }

    fun onBackClick() {
        navBack.navigate()
    }

    fun onRefresh() {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            profileScreenUIModel.value = LoadableData.Loading
            getNurtureProfile().fold(
                onSuccess = {
                    // Keeps the account's name in the switcher up to date.
                    accountManager.updateActiveDisplayName("${it.firstName} ${it.lastName}".trim())
                    profileScreenUIModel.value = LoadableData.Loaded(it.toProfileScreenUIModel())
                },
                onFailure = {
                    profileScreenUIModel.value = LoadableData.Failed(
                        it.message ?: "در دریافت پروفایل مشکلی پیش آمده است."
                    )
                }
            )
        }

    }
}
