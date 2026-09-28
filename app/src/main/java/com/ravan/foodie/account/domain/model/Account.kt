package com.ravan.foodie.account.domain.model

import com.ravan.foodie.domain.model.SamadToken
import kotlinx.serialization.Serializable

/**
 * A Samad account saved on this device. [username] is the Samad username and identifies the
 * account everywhere, including per-account rows in the local databases.
 */
@Serializable
data class Account(
    val username: String,
    // TODO(security): the password is stored in plaintext so a dead refresh token can be replaced
    //  without asking again. See https://github.com/erfnzdeh/foodie/issues/1
    val password: String,
    val accessToken: String = "",
    val refreshToken: String = "",
    /** Full name from Samad, filled in after the first successful login. */
    val displayName: String? = null,
    val lastUsedAt: Long = 0L,
    /** Set when Samad rejected the stored password; cleared by logging in again. */
    val needsReauth: Boolean = false,
) {
    val token: SamadToken?
        get() = if (accessToken.isNotEmpty() && refreshToken.isNotEmpty()) {
            SamadToken(accessToken = accessToken, refreshToken = refreshToken)
        } else {
            null
        }

    val label: String
        get() = displayName?.takeIf { it.isNotBlank() } ?: username

    // Keeps credentials out of logs and crash reports.
    override fun toString(): String =
        "Account(username=$username, displayName=$displayName, needsReauth=$needsReauth)"
}
