package com.blur.blur.presentation.Main.SendMessage

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun SendMessageNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SendMessageRoute.MessageUserListScreen.name,
    ) {
        composable(route = "${SendMessageRoute.SendMessageScreen.name}/{username}",
            arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )) { destination ->
            // SendMessageScreen으로 전달할 사용자 이름 가져오기
            val username = destination.arguments?.getString("username") ?: ""
            SendMessageScreen(username = username)
        }
        composable(route = SendMessageRoute.MessageUserListScreen.name) {
            MessageUserListScreen(
                navigateToSendMessageScreen = { username ->
                    navController.navigate(
                        route = "${SendMessageRoute.SendMessageScreen.name}/$username"
                    )
                }
            )
        }
    }
}
