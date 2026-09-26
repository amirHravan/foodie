package com.ravan.foodie.login.domain.model

enum class LoginMode {
    /** No usable account yet; back leaves the app. */
    Initial,

    /** Adding another account from Profile; back returns there. */
    Add,

    /** A saved account's password stopped working; the username is fixed. */
    Reauth,
}
