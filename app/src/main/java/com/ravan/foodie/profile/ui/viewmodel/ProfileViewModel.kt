package com.ravan.foodie.profile.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.ui.viewmodel.RavanViewModel
import com.ravan.foodie.domain.usecase.GetSavedSamadTokenUseCase
import com.ravan.foodie.profile.domain.usecase.GetNurtureProfile
import com.ravan.foodie.profile.ui.model.toProfileScreenUIModel
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getSavedSamadTokenUseCase: GetSavedSamadTokenUseCase,
    private val getNurtureProfile: GetNurtureProfile,
): RavanViewModel() {

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
            getSavedSamadTokenUseCase()?.let {token ->
                profileUIModel.value = LoadableData.Loading
                getNurtureProfile(token).fold(
                    onSuccess = {
                        profileUIModel.value = LoadableData.Loaded(it.toProfileScreenUIModel())
                    },
                    onFailure = {
                        profileUIModel.value = LoadableData.Failed(it.message ?: "در دریافت پروفایل مشکلی پیش آمده است.")
                    }
                )
            }
        }
    }
}