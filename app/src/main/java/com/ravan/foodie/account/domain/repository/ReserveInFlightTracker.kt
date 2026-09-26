package com.ravan.foodie.account.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

/**
 * Counts reserve/cancel requests that are still running, so switching accounts can wait for them
 * instead of letting a result land on the wrong account.
 */
class ReserveInFlightTracker {

    private val count = MutableStateFlow(0)

    /** Number of reserve/cancel requests currently running. */
    val inFlight: StateFlow<Int> = count.asStateFlow()

    suspend fun <T> track(block: suspend () -> T): T {
        count.update { it + 1 }
        try {
            return block()
        } finally {
            count.update { it - 1 }
        }
    }

    suspend fun awaitIdle() {
        count.first { it == 0 }
    }
}
