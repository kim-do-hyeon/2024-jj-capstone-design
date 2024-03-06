package com.example.blur.Screen.AccountManagement

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blur.components.Button.FillBtn
import com.example.blur.components.Button.TwoText
import com.example.blur.components.Text.MessageText
import com.example.blur.components.Text.MessageText2
import com.example.blur.components.TextField.EmailTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindPasswordScreen(
    navController: NavHostController
) {

    var emailError by remember { mutableStateOf(false) } // 이메일 유효성 검사 결과를 나타내는 변수

    var userEmail by remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "비밀번호 찾기") },
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
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 53.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(contentPadding)
            ) {
                Spacer(modifier = Modifier.height(210.dp))

                MessageText(
                    "암호를 잊어버렸습니까?",
                    30
                )

                Spacer(modifier = Modifier.height(26.dp))

                MessageText2(
                    "걱정하지 마세요! 계정과 연결된 이메일 주소를 \n 입력하시면 메일로 비밀번호를 보내 드립니다.",
                    20
                )

                Spacer(modifier = Modifier.height(35.dp))

                EmailTextField(
                    onValueChange = {
                        userEmail = it
                        emailError = !Patterns.EMAIL_ADDRESS.matcher(it).matches()
                    }
                )

                Spacer(modifier = Modifier.height(13.dp))

                FillBtn(" 메일전송")

                Spacer(modifier = Modifier.height(202.dp))

                TwoText(
                    "비밀번호가 기억났나요?",
                    "로그인",
                    onClick = {
                        navController.navigate("Login")
                    }
                )
            }
        },

        )
}


@Preview
@Composable
fun FindPasswordScreenPreview() {
    FindPasswordScreen(rememberNavController())
}