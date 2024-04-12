package com.example.blur.presentation.Main.Setting.ChangeEmail


sealed class ChangeEmailRoute(
    val name:String
) {
    object ChangeEmailScreen : ChangeEmailRoute("ChangeEmailScreen")
}