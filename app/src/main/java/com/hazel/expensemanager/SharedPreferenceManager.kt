package com.hazel.expensemanager

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("ExpensePrefs", Context.MODE_PRIVATE)

    fun getLogin(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }
    fun saveLogin(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }
}