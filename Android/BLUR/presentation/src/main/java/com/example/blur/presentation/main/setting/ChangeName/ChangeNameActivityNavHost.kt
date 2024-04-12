package com.example.blur.presentation.Main.Setting.ChangeName

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blur.presentation.Main.Setting.ChangeEmail.ChangeEmailRoute
import com.example.blur.presentation.Main.Setting.ChangeEmail.ChangeEmailScreen


@Composable
fun ChangeNameActivityNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ChangeNameRoute.ChangeNameScreen.name,
    ) {

        composable(route = ChangeNameRoute.ChangeNameScreen.name) {
            ChangeNameScreen()
        }

    }
}
