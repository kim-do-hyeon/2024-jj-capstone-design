package com.example.blur


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blur.components.Text.BLUE_Title
import com.example.blur.components.Button.FillBtn
import com.example.blur.components.Button.FindPassword
import com.example.blur.components.TextField.IDTextField
import com.example.blur.components.Button.OutLineBtn
import com.example.blur.components.Text.SubTitle
import com.example.blur.components.TextField.PasswordTextField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    var userID by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text(text = "Login") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate("Start")
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
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 53.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(contentPadding)
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo_m),
                contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .heightIn(220.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            BLUE_Title()

            Spacer(modifier = Modifier.height(11.dp))

            SubTitle()

            Spacer(modifier = Modifier.height(33.dp))

            IDTextField(onValueChange = { userID = it })

            PasswordTextField(label = "Password", onValueChange = { password = it })

            Spacer(modifier = Modifier.height(30.dp))

            FillBtn(
                value = "로그인",
                onClick = {
                    if (userID.isNotEmpty() && password.isNotEmpty()) {
                        // 아이디와 비밀번호가 모두 입력된 경우에만 로그인 로직 수행
                        // 여기에 로그인 로직을 추가하세요
                    } else {
                        // 아이디나 비밀번호가 비어 있는 경우 에러 메시지 표시 또는 다른 작업 수행
                    }
                },
                enabled = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutLineBtn("회원가입",
                onClick = {
                    navController.navigate("SingUp")
                })

            Spacer(modifier = Modifier.height(16.dp))

            FindPassword("비밀번호 찾기 >",
                onClick = {
                    navController.navigate("FindPassword")
                }
            )


            Spacer(modifier = Modifier.height(26.dp))
        }
    }
}


@Preview
@Composable
fun LoginScreenPrevicew() {
    LoginScreen(rememberNavController())
}