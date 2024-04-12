

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.blur.presentation.Main.Setting.ChangeEmail.ChangeEmailRoute
import com.example.blur.presentation.Main.Setting.ChangeEmail.ChangeEmailScreen


@Composable
fun ChangeEmailActivityNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ChangeEmailRoute.ChangeEmailScreen.name,
    ) {

        composable(route = ChangeEmailRoute.ChangeEmailScreen.name) {
            ChangeEmailScreen()
        }

    }
}
