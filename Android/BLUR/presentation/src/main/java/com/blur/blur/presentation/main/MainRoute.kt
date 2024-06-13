package com.blur.blur.presentation.Main

sealed class MainRoute(
    val name:String
) {
    object MainScreen : MainRoute("MainScreen")

}