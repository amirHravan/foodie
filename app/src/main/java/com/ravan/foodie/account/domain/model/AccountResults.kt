package com.ravan.foodie.account.domain.model

/** Outcome of opening the app with the saved active account. */
sealed interface ResumeResult {
    data object Resumed : ResumeResult

    /** No saved accounts: show the login screen. */
    data object NoAccount : ResumeResult

    /** The active account couldn't log in; show login for it. */
    data class NeedsLogin(val username: String, val error: AuthException) : ResumeResult
}

/** Outcome of removing an account. */
sealed interface RemoveResult {
    /** A non-active account was removed; nothing else changed. */
    data object Removed : RemoveResult

    /** The active account was removed and [username] is active now. */
    data class SwitchedTo(val username: String) : RemoveResult

    /** No account could take over; show the login screen, prefilled with [username] if set. */
    data class NeedsLogin(val username: String?) : RemoveResult
}

class AccountLimitException(message: String) : Exception(message)
