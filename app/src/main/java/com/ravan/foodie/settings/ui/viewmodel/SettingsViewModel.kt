package com.ravan.foodie.settings.ui.viewmodel

import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.model.PreferencesManager
import com.ravan.foodie.domain.ui.viewmodel.FoodieViewModel
import com.ravan.foodie.domain.util.SharedPrefKeys

class SettingsViewModel(
    private val preferencesManager: PreferencesManager
) : FoodieViewModel() {

    val navBack: NavigationEvent = NavigationEvent()
    val navAboutUs: NavigationEvent = NavigationEvent()

    fun onLaunch() {

    }

    fun onReload() {
        onLaunch()
    }

    fun onBackClick() {
        navBack.navigate()
    }

    fun onNotificationToggleChange(
        newState: Boolean,
    ) {
        preferencesManager.putBoolean(
            SharedPrefKeys.NotificationsEnabled.key,
            newState
        )
    }

    fun onAboutUsClick() {
        navAboutUs.navigate()
    }

    fun onLogoutClick(
        onFinish: () -> Unit,
    ) {
        preferencesManager.remove(SharedPrefKeys.RefreshToken.key)
        preferencesManager.remove(SharedPrefKeys.Username.key)
        preferencesManager.remove(SharedPrefKeys.Password.key)
        preferencesManager.remove(SharedPrefKeys.AccessToken.key)
        onFinish()
    }

    fun onSendFeedBackClick(onFinish: () -> Unit) {
        onFinish()
    }
}