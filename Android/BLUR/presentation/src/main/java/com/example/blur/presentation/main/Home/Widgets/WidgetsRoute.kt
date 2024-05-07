package com.example.blur.presentation.Main.Home.Widgets

sealed class WidgetsRoute (
    val name: String
){
    object WidgetsSettingsScreen: WidgetsRoute("WidgetsSettingsScreen")
    object WidgetsListDialog:WidgetsRoute("WidgetsListDialog")
}