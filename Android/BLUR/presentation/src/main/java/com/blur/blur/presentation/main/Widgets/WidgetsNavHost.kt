package com.blur.blur.presentation.Main.Widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blur.blur.presentation.Main.Home.Widgets.WidgetsSettingsScreen

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