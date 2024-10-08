package com.blur.blur.presentation.Login

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions

/**
 * @author soohwan.ok
 */
@Composable
fun LoginNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LoginRoute.WelcomeScreen.name,
    ) {
        composable(route = LoginRoute.WelcomeScreen.name) {
            WelcomeScreen(
                onNavigateToLoginScreen = {
                    navController.navigate(route = LoginRoute.LoginScreen.name)
                },
                onNavigateToSignUpScreen = {
                    navController.navigate(route = LoginRoute.SignUpScreen.name)
                }
            )
        }

        composable(route = LoginRoute.LoginScreen.name) {
            LoginScreen(
                onNavigateToSignUpScreen = {
                    navController.navigate(
                        LoginRoute.SignUpScreen.name
                    )
                },
                onNavigateToFindPasswordScreen = {
                    navController.navigate(
                        route = LoginRoute.FindPasswordScreen.name
                    )
                }
            )
        }

        composable(route = LoginRoute.SignUpScreen.name) {
            SignUpScreen(
                onNavigateToLoginScreen = {
                    navController.navigate(
                        route = LoginRoute.LoginScreen.name,
                        navOptions = navOptions {
                            popUpTo(LoginRoute.WelcomeScreen.name)
                        }
                    )
                },
                onNavigateToFindPasswordScreen = {
                    navController.navigate(
                        route = LoginRoute.FindPasswordScreen.name
                    )
                }
            )
        }

        composable(route = LoginRoute.FindPasswordScreen.name) {
            FindPasswordScreen(
            )
        }
    }

}

