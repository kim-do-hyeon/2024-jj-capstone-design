package com.blur.blur.presentation.Main.Camera

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blur.blur.presentation.Main.MainActivity

@Composable
fun CameraNavHost() {
    val navController = rememberNavController()
    val sharedViewModel: GalleryViewModel = viewModel()
    val context = LocalContext.current
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
                ,onNavigateSuccessImage={
                    navController.navigate(route=CameraRoute.SuccessImage.name)
                }
            )
        }
        composable(route=CameraRoute.ImageSelectScreen.name){
            ImageSelectScreen(
                viewmodel = sharedViewModel,
                onNavigateUpLoadImageScreen={
                    navController.navigate(route=CameraRoute.UpLoadImageScreen.name)
                },
                onNavigateSuccessImage= {
                    navController.navigate(route=CameraRoute.SuccessImage.name)
                }
            )
        }
        composable(route=CameraRoute.SuccessImage.name){
            SuccessImage(
                onNavigateHome = {
                    context.startActivity(Intent(context, MainActivity::class.java))
                }
            )
        }
    }
}