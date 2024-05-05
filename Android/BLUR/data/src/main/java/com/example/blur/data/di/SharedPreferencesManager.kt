package com.example.blur.data.di

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import android.util.Base64


object SharedPreferencesManager {
    private const val COOKIE_PREFS_NAME = "CookiePrefs"
    private const val USERNAME_KEY = "username"



    fun saveCookie(context: Context, cookie: String) {
        val sharedPreferences = context.getSharedPreferences(COOKIE_PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("cookie", cookie)
            apply()
        }
    }

    fun getCookie(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(COOKIE_PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString("cookie", null)
    }

    fun clearCookie(context: Context) {
        val sharedPreferences = context.getSharedPreferences(COOKIE_PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove("cookie")
            apply()
        }
    }

    fun saveUsername(context: Context, username: String) {
        val sharedPreferences = context.getSharedPreferences(COOKIE_PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(USERNAME_KEY, username)
            apply()
        }
    }

    fun getUsername(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(COOKIE_PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(USERNAME_KEY, null)
    }

    fun clearUsername(context: Context) {
        val sharedPreferences = context.getSharedPreferences(COOKIE_PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove(USERNAME_KEY)
            apply()
        }
    }


}
