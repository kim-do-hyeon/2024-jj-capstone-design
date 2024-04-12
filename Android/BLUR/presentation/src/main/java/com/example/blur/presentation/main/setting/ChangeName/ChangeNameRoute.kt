package com.example.blur.presentation.Main.Setting.ChangeName


sealed class ChangeNameRoute(
    val name:String
) {
    object ChangeNameScreen : ChangeNameRoute("ChangeNameScreen")
}