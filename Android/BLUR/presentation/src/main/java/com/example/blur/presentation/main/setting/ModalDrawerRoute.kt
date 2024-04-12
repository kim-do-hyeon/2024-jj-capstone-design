package com.example.blur.presentation.Main.Setting

sealed class ModalDrawerRoute(
    val name:String
) {
    object ChangePasswordScreen : ModalDrawerRoute("ChangePasswordScreen")
}