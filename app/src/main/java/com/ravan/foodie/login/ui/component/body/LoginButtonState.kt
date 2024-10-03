package com.ravan.foodie.login.ui.component.body

enum class LoginButtonState {
    Loading,
    Enable,
    Disabled;

    fun isButtonEnable(): Boolean {
        return this == Enable
    }
}