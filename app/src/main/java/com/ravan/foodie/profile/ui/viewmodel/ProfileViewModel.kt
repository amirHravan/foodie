package com.ravan.foodie.profile.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.ui.viewmodel.FoodieViewModel
import com.ravan.foodie.profile.domain.usecase.GetNurtureProfile
import com.ravan.foodie.profile.ui.model.toProfileScreenUIModel
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getNurtureProfile: GetNurtureProfile,
) : FoodieViewModel() {

    val profileUIModel = mutableStateOf<LoadableData>(LoadableData.NotLoaded)
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
            profileUIModel.value = LoadableData.Loading
            getNurtureProfile().fold(
                onSuccess = {
                    profileUIModel.value = LoadableData.Loaded(it.toProfileScreenUIModel())
                },
                onFailure = {
                    profileUIModel.value = LoadableData.Failed(
                        it.message ?: "در دریافت پروفایل مشکلی پیش آمده است."
                    )
                }
            )
        }

    }
}