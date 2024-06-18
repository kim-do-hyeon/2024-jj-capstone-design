package com.blur.blur.presentation.Main.Widgets

sealed class WidgetsRoute (
    val name: String
){
    object WidgetsSettingsScreen: WidgetsRoute("WidgetsSettingsScreen")
    object WidgetsListDialog:WidgetsRoute("WidgetsListDialog")
}