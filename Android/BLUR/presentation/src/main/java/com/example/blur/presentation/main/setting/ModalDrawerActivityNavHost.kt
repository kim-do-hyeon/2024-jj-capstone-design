package com.example.blur.presentation.Main.Setting

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blur.presentation.Login.LoginRoute
import com.example.blur.presentation.Main.Setting.ChangePassword.ChangePasswordScreen


@Composable
fun ModalDrawerActivityNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ModalDrawerRoute.ChangePasswordScreen.name,
    ) {
        composable(route = ModalDrawerRoute.ChangePasswordScreen.name) {
            ChangePasswordScreen(viewModel = hiltViewModel(), onNavigateToLoginScreen = {
                // 로그인 화면으로 이동하는 함수를 호출합니다.
                navController.navigate(LoginRoute.WelcomeScreen.name) {
                    // 역방향으로 이동할 수 없도록 back stack을 제거합니다.
                    popUpTo(LoginRoute.WelcomeScreen.name) {
                        inclusive = true
                    }
                }
            })
        }

    }
}