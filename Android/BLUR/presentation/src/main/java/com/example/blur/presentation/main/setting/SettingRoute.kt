package com.example.blur.presentation.login

/**
 * @author soohwan.ok
 */
sealed class SettingRoute(val name: String) {
    object ChangePasswordScreen : SettingRoute("ChangePasswordScreen")
}