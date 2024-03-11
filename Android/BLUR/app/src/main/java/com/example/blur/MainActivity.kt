package com.example.blur

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blur.Screen.AccountManagement.SingUpCompleteScreen
import com.example.blur.Screen.AccountManagement.StartScreen
import com.example.blur.Screen.AddDeviceScreen
import com.example.blur.Screen.HomeScreen
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
fun NavigationView() {
    val navController = rememberNavController()

    // 예시로 기기 등록 상태를 추적하고 있다고 가정
    var deviceRegistered by remember { mutableStateOf(false) }

    NavHost(navController = navController, startDestination = "Start") {
        composable("Start") { StartScreen(navController) }
        composable("Login") { LoginScreen(navController) }
        composable("SingUp") { SingUpScreen(navController) }
        composable("SingUpComplete") { SingUpCompleteScreen(navController) }
        composable("FindPassword") { FindPasswordScreen(navController) }
        composable("Home") { HomeScreen(navController, deviceRegistered) } // deviceRegistered 전달
        composable("AddDevice") { AddDeviceScreen(navController) }
    }
}
