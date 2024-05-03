package com.example.blur.presentation.Main.Home.Camera

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blur.presentation.Login.LoginRoute

@Composable
fun CameraNavHost() {
    val navController = rememberNavController()
    val sharedViewModel: GalleryViewModel = viewModel()
    NavHost(
        navController = navController ,
        startDestination =CameraRoute.UpLoadImageScreen.name,
    ) {
        composable(route=CameraRoute.UpLoadImageScreen.name){
            UpLoadImageScreen(
                viewmodel = sharedViewModel,
                onNavigateToImageSelectScreen ={
                    navController.navigate(route=CameraRoute.ImageSelectScreen.name)
                }
            )
        }
        composable(route=CameraRoute.ImageSelectScreen.name){
            ImageSelectScreen(
                viewmodel = sharedViewModel,
                onNavigateUpLoadImageScreen={
                    navController.navigate(route=CameraRoute.UpLoadImageScreen.name)
                }
            )
        }
    }
}