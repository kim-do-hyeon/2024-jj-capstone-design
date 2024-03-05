package com.example.blur

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blur.ui.theme.BLURTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BLURTheme {
                // A surface container using the 'background' color from the theme
                NavigationView()
            }
        }
    }
}

@Composable
fun NavigationView(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Start" ){
        composable("Start"){ StartScreen(navController)}
        composable("Login"){ LoginScreen(navController)}
        composable("SingUp"){ SingUpScreen(navController)}
        composable("SingUpComplete"){ SingUpCompleteScreen(navController) }
        composable("FindPassword"){ FindPasswordScreen(navController) }
    }
}