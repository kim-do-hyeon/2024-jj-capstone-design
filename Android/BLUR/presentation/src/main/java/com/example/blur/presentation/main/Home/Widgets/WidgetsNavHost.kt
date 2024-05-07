package com.example.blur.presentation.Main.Home.Widgets

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun WidgetsNavHost() {
    val navController = rememberNavController()
    val sharedViewModel: WidgetsSettingViewModel = viewModel()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = WidgetsRoute.WidgetsSettingsScreen.name
    ) {
        composable(route = WidgetsRoute.WidgetsSettingsScreen.name) {
            WidgetsSettingsScreen(
                viewModel = sharedViewModel,
                onDialog = {
                    navController.navigate(route = WidgetsRoute.WidgetsListDialog.name)
                },
            )
        }


    }
}