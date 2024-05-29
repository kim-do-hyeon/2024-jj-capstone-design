package com.blur.blur.presentation.Main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.blur.blur.presentation.Main.Home.HomeScreen
import com.blur.blur.presentation.Main.Setting.ModalDrawerSheetScreen
import com.blur.blur.presentation.theme.BLURTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()




    Surface {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheetScreen()
            },
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                        title = {},
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                }
                            ) {
                                Icon(Icons.Filled.Menu, contentDescription = "메뉴")
                            }
                        }
                    )
                },
                content = { padding ->
                    // Apply padding to HomeScreen
                    HomeScreen(modifier = Modifier.padding(padding))
                }
            )
        }
    }
}


@Preview
@Composable
fun MainNavHostPreview() {
    BLURTheme {
        MainNavHost()
    }
}