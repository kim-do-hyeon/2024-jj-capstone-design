package com.example.blur.presentation.Main.Home.Widgets

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blur.presentation.Main.Home.Camera.CameraRoute
import com.example.blur.presentation.Main.Home.Camera.GalleryViewModel
import com.example.blur.presentation.Main.Home.Camera.ImageSelectScreen
import com.example.blur.presentation.Main.Home.Camera.SuccessImage
import com.example.blur.presentation.Main.Home.Camera.UpLoadImageScreen
import com.example.blur.presentation.Main.MainActivity

@Composable
fun WidgetsNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(
        navController = navController ,
        startDestination =WidgetsRoute.WidgetsSettingsScreen.name
    ) {
        composable(route=WidgetsRoute.WidgetsSettingsScreen.name){
            WidgetsSettingsScreen()
        }

    }
}