package com.ravan.foodie.account.ui.model

import com.ravan.foodie.login.domain.model.LoginMode

sealed interface AccountNavEvent {
    /** The active account changed: start over from the home screen. */
    data object Home : AccountNavEvent

    /**
     * Open the login screen. [clearStack] is set when no account is usable anymore, so there is
     * nothing to go back to.
     */
    data class Login(
        val mode: LoginMode,
        val username: String? = null,
        val clearStack: Boolean = false,
    ) : AccountNavEvent
}
