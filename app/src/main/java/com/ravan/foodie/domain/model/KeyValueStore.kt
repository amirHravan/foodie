package com.ravan.foodie.domain.model

/**
 * Minimal string storage, so code that persists small values can be tested without Android.
 */
interface KeyValueStore {
    fun getString(key: String): String?

    fun putString(key: String, value: String)

    fun remove(key: String)
}
