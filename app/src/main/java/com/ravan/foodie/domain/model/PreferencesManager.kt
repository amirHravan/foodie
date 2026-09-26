package com.ravan.foodie.domain.model

import android.content.Context
import android.content.SharedPreferences


class PreferencesManager(context: Context) : KeyValueStore {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("", Context.MODE_PRIVATE)

    override fun remove(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    override fun putString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    override fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun putBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }
}