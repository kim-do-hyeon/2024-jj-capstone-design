package com.example.blur.presentation.main.setting

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blur.presentation.component.ProfileImage
import com.example.blur.presentation.login.LoginActivity
import com.example.blur.presentation.theme.BLURTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    onPasswordChange: () -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.collectAsState().value
    val cookie by viewModel.cookie.collectAsState()
    val sessionTestResult by viewModel.sessionTestResult.collectAsState()


    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            SettingSideEffect.NavigateToLoginActivity -> {
                context.startActivity(
                    Intent(context, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
            }
        }
    }
    // SettingScreen 내부의 로그아웃 버튼을 렌더링
    SettingScreen(
        username = "",
        onLogoutClick = viewModel::onLogoutClick,
        onPasswordChange = onPasswordChange,
        cookie = cookie, // 쿠키 값을 전달
        sessionTestResult = sessionTestResult // sessionTestResult 값을 전달
    )
}

@Composable
fun SettingScreen(
    username: String,
    onLogoutClick: () -> Unit,
    onPasswordChange: () -> Unit,
    cookie: String?, // 쿠키 값을 매개변수로 받음
    sessionTestResult: String? // sessionTestResult 값을 매개변수로 받음
) {


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()

    ) {
        Spacer(modifier = Modifier.weight(1f))

        Box {
            ProfileImage(
                modifier = Modifier.size(150.dp),
                profileImageUrl = null,
            )

            IconButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                onClick = { /*TODO*/ }
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .border(
                            width = 1.dp,
                            color = androidx.compose.ui.graphics.Color.Gray,
                            shape = CircleShape
                        )
                        .background(
                            color = androidx.compose.ui.graphics.Color.White,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(20.dp),
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

            }
        }

        Text(
            modifier = Modifier
                .padding(top = 10.dp),
            text = username,
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            onClick = onPasswordChange
        ) {
            Text("비밀번호변경")
        }

        Button(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            onClick = onLogoutClick
        ) {
            Text("로그아웃")
        }

        Spacer(modifier = Modifier.weight(1f))
        // 쿠키 값 표시
        Text(text = cookie ?: "쿠키가 없습니다.")

        // 세션 테스트 결과 표시
        Spacer(modifier = Modifier.weight(1f))

        Text(text = "Session Test Result: $sessionTestResult")

    }
}


@Preview
@Composable
private fun SettingScreenPreview() {
    BLURTheme {
        Surface {
            SettingScreen(
                username = "여범휘",
                onLogoutClick = {},
                onPasswordChange = {},
                cookie = "",
                sessionTestResult = ""
            )
        }
    }
}