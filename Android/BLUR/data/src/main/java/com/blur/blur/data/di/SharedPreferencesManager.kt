package com.blur.blur.data.di

import android.content.Context


object SharedPreferencesManager {
    private const val COOKIE_PREFS_NAME = "CookiePrefs"
    private const val USERNAME_KEY = "username"
    private const val COOKIE_KEY = "cookie"
    private const val COOKIE_EXPIRY_KEY = "cookie_expiry"
    private const val PRODUCT_PREFS_NAME = "ProductPrefs"
    private const val PRODUCT_CODE_KEY = "productCode"


    fun saveCookie(context: Context, cookie: String, expiryTimeMillis: Long) {
        val sharedPreferences = context.getSharedPreferences(COOKIE_PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(COOKIE_KEY, cookie)
            putLong(COOKIE_EXPIRY_KEY, expiryTimeMillis)
            apply()
        }
    }

    fun getCookie(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(COOKIE_PREFS_NAME, Context.MODE_PRIVATE)
        val expiryTimeMillis = sharedPreferences.getLong(COOKIE_EXPIRY_KEY, -1)
        if (expiryTimeMillis != -1L && System.currentTimeMillis() >= expiryTimeMillis) {
            // Cookie has expired, clear it
            clearCookie(context)
            return null
        }
        return sharedPreferences.getString(COOKIE_KEY, null)
    }

    fun clearCookie(context: Context) {
        val sharedPreferences = context.getSharedPreferences(COOKIE_PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove(COOKIE_KEY)
            remove(COOKIE_EXPIRY_KEY)
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

    fun saveProductCode(context: Context, code: String) {
        val sharedPreferences = context.getSharedPreferences(PRODUCT_PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(PRODUCT_CODE_KEY, code)
            apply()
        }
    }

    fun getProductCode(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PRODUCT_PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(PRODUCT_CODE_KEY, null)
    }

    fun clearProductCode(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PRODUCT_PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove(PRODUCT_CODE_KEY)
            apply()
        }
    }


}
