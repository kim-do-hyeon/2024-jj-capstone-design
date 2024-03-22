package com.example.blur.Screen.AccountManagement.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blur.R
import com.example.blur.components.Text.BLUE_Title
import com.example.blur.components.Button.FillBtn
import com.example.blur.components.Button.FindPassword
import com.example.blur.components.Button.OutLineBtn
import com.example.blur.components.Text.SubTitle

@Composable
fun StartScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 53.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_l),
                contentDescription = null,
                modifier = Modifier
                    .width(120.dp)
                    .heightIn(170.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(104.dp))

            BLUE_Title()

            Spacer(modifier = Modifier.height(11.dp))

            SubTitle()

            Spacer(modifier = Modifier.height(44.dp))

            FillBtn("시작하기",
                onClick = {
                    navController.navigate("Login")
                })

            Spacer(modifier = Modifier.height(16.dp))

            OutLineBtn("회원가입",
                onClick = {
                    navController.navigate("SingUp")
                })

            Spacer(modifier = Modifier.height(26.dp))

            FindPassword("비밀번호 찾기 >",
                onClick = {
                    navController.navigate("FindPassword")
                }
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}


@Preview
@Composable
fun StartScreenPrevicew() {
    StartScreen(rememberNavController())
}