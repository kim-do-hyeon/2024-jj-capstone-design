package com.example.blur.Screen.AccountManagement.Login

import android.content.Context
import android.preference.PreferenceManager

object SharedPreferencesManager {
    private const val PREF_NAME = "MyPrefs"
    private const val KEY_USER_ID = "userId"
    private const val SESSION_TOKEN_KEY = "session_token"


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

    fun saveSessionToken(context: Context, sessionToken: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().putString(SESSION_TOKEN_KEY, sessionToken).apply()
    }

    fun getSessionToken(context: Context): String? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(SESSION_TOKEN_KEY, null)
    }
}