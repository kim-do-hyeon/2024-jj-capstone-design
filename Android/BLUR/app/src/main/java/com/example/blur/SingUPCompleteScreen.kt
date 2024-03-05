package com.example.blur

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blur.components.Button.FillBtn
import com.example.blur.components.Text.MessageText
import com.example.blur.components.Text.MessageText2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingUpCompleteScreen(
    navController: NavHostController
) {
    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text(text = "회원가입 완료") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("Login") }
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Localized description")
                    }
                }
            )
        },
        content = { contentPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 53.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(contentPadding)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_check_circle_24),
                    contentDescription = null,
                    modifier = Modifier
                        .width(200.dp)
                        .heightIn(220.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(35.dp))

                MessageText(
                    "회원가입 완료",
                    26
                )

                Spacer(modifier = Modifier.height(8.dp))

                MessageText2(
                    "BLUR에 가입해주셔서\n감사합니다.",
                    15
                )

                Spacer(modifier = Modifier.height(200.dp))

                FillBtn(
                    "로그인하러 가기",
                    onClick = {
                        navController.navigate("Login")
                    }
                )

                Spacer(modifier = Modifier.height(80.dp))

            }
        }
    )
}


@Preview
@Composable
fun SingUpCompleteScreenPrevicew() {
    SingUpCompleteScreen(rememberNavController())
}