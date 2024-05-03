package com.example.blur.presentation.Main.Setting.ChangePassword

sealed class ChangePasswordRoute(
    val name:String
) {
    object ChangePasswordScreen : ChangePasswordRoute("ChangePasswordScreen")
}