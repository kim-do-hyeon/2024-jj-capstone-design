package com.example.blur.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class MainRoute(
    val route: String,
    val contentDescription: String,
    val icon: ImageVector
) {
    HOME(route = "HomeScreen", contentDescription = "홈", icon = Icons.Filled.Home),
    SETTING(route = "com.example.blur.presentation.main.setting.SettingScreen", contentDescription = "내정보", icon = Icons.Filled.AccountCircle),
}
