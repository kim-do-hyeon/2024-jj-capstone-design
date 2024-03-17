package com.example.blur.Screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blur.R
import com.example.blur.Screen.CameraX.CameraXActivity
import com.example.blur.components.ListItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "스마트미러") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate("Home")
                        }
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Localized description")
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(contentPadding)
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            Image(
                painter = painterResource(id = R.drawable.logo_m),
                contentDescription = null,
                modifier = Modifier
                    .width(330.dp)
                    .heightIn(230.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))

            ListItems(
                headlineText = "얼굴 등록",
                supportingText = "나의 사진을 찍어서 기기에 얼굴을 등록해보세요",
                icon = Icons.Filled.Person,
                onClick = {
                    context.startActivity(Intent(context, CameraXActivity::class.java))
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            ListItem(
                headlineContent = { Text("제목") },
                overlineContent = { Text(text = "오버라인")},
                supportingContent = { Text(text = "서포팅")},
                leadingContent = {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = "Localized description",
                    )
                },
                trailingContent = { Text(text = "trailing")}
            )

            ListItem(
                headlineContent = { Text("제목") },
                overlineContent = { Text(text = "오버라인")},
                supportingContent = { Text(text = "서포팅")},
                leadingContent = {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = "Localized description",
                    )
                },
                trailingContent = { Text(text = "trailing")}
            )

            ListItem(
                headlineContent = { Text("제목") },
                overlineContent = { Text(text = "오버라인")},
                supportingContent = { Text(text = "서포팅")},
                leadingContent = {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = "Localized description",
                    )
                },
                trailingContent = { Text(text = "trailing")}
            )

        }
    }
}

@Preview
@Composable
fun DeviceScreenPreview() {
    DeviceScreen(rememberNavController())
}
