package com.example.blur.Screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blur.Screen.AccountManagement.Login.SharedPreferencesManager
import com.example.blur.components.Card.SmartMirrorCard
import com.example.blur.components.Text.HomeTopText


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    deviceRegistered: Boolean
) {
    val context = LocalContext.current
    val userId = remember { SharedPreferencesManager.getUserId(context) }
    val sessionToken = remember { SharedPreferencesManager.getSessionToken(context) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "홈") },
                navigationIcon = {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(Icons.Filled.Menu, contentDescription = "Localized description")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("AddDevice") }) {
                Icon(Icons.Filled.Add, contentDescription = "add")
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            HomeTopText()
            SmartMirrorCard(
                deviceName = "스마트미러", // 여기서 각 디바이스 이름을 전달
                onClick = {
                    // TODO: Detail view navigation
                    navController.navigate("Device")
                }
            )
            Text(text = "저장된 아이디: $userId") // 아이디 표시
            Text(text = "저장된 세션 토큰: $sessionToken") // 세션 토큰 표시
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        rememberNavController(),
        deviceRegistered = true
    )
}