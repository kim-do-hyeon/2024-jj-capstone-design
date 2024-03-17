package com.example.blur.Screen.AccountManagement.Login

import android.content.Context

object SharedPreferencesManager {
    private const val PREF_NAME = "MyPrefs"
    private const val KEY_USER_ID = "userId"

    fun saveUserId(context: Context, userId: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USER_ID, userId)
        editor.apply()
    }

    fun getUserId(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    fun clearUserId(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(KEY_USER_ID)
        editor.apply()
    }
}
