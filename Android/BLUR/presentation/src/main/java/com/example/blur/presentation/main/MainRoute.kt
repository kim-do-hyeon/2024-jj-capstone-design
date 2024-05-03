package com.example.blur.presentation.Main

sealed class MainRoute(
    val name:String
) {
    object HomeScreen : MainRoute("HomeScreen")

}