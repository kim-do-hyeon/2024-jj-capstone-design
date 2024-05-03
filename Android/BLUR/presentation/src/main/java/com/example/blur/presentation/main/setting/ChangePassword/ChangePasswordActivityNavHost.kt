package com.example.blur.presentation.Main.Setting.ChangePassword

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun ChangePasswordActivityNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ChangePasswordRoute.ChangePasswordScreen.name,
    ) {

        composable(route = ChangePasswordRoute.ChangePasswordScreen.name) {
            ChangePasswordScreen()

        }

    }
}
