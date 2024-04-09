package com.example.blur.presentation.main

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blur.presentation.login.LoginRoute
import com.example.blur.presentation.login.SettingRoute
import com.example.blur.presentation.main.Home.HomeScreen
import com.example.blur.presentation.main.setting.ChangePassword.ChangePasswordScreen
import com.example.blur.presentation.main.setting.SettingScreen
import com.example.blur.presentation.theme.BLURTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current
    Surface {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                    title = {},
                    navigationIcon = {
                        // 뒤로 가기 버튼에 뒤로 가기 기능을 추가합니다.
                        IconButton(onClick = {
                            (context as? Activity)?.onBackPressed()
                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "뒤로 가기")
                        }
                    }
                )
            },
            content = { padding ->
                NavHost(
                    modifier = Modifier.padding(padding),
                    navController = navController,
                    startDestination = MainRoute.HOME.route
                ) {
                    composable(route = MainRoute.HOME.route) {
                        HomeScreen()
                    }
                    composable(route = MainRoute.SETTING.route) {
                        SettingScreen(
                            onPasswordChange = {
                                navController.navigate(
                                    route = SettingRoute.ChangePasswordScreen.name
                                )
                            }
                        )
                    }
                    composable(route = SettingRoute.ChangePasswordScreen.name) {
                        ChangePasswordScreen(
                            onNavigateToLoginScreen = {
                                navController.navigate(
                                    route = LoginRoute.LoginScreen.name
                                )
                            }
                        )
                    }
                }
            },
            bottomBar = {
                MainBottomBar(
                    navController = navController
                )
            }
        )
    }
}

@Preview
@Composable
fun MainNavHostPreview() {
    BLURTheme {
        MainNavHost()
    }
}