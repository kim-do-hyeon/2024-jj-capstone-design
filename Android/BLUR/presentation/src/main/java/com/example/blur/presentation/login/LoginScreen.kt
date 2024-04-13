package com.example.blur.presentation.Login

import PasswordTextField
import UsernameTextField
import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blur.components.Text.BLUE_Title
import com.example.blur.components.Text.SubTitle
import com.example.blur.presentation.Main.MainActivity
import com.example.blur.presentation.R
import com.example.blur.presentation.Component.Button.FillButton
import com.example.blur.presentation.Component.Button.FindPassword
import com.example.blur.presentation.Component.Button.SignUpBtn

import com.example.blur.presentation.theme.BLURTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

/**
 * @author soohwan.ok
 */

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToSignUpScreen: () -> Unit,
    onNavigateToFindPasswordScreen: () -> Unit,
) {
    val state = viewModel.collectAsState().value
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            LoginSideEffect.NavigateToMainActivity -> {
                context.startActivity(
                    Intent(
                        context, MainActivity::class.java
                    ).apply {
                        //로그인화면 못가게 하는거
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
            }
        }
    }


    LoginScreen(
        username = state.username,
        password = state.password,
        onUsernameChange = viewModel::onIdChange,
        onPasswordChange = viewModel::onPasswordChange,
        onNavigateToSignUpScreen = onNavigateToSignUpScreen,
        onLoginClick = viewModel::onLoginClick,
        onNavigateToFindPasswordScreen = onNavigateToFindPasswordScreen,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun LoginScreen(
    username: String,
    password: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onNavigateToSignUpScreen: () -> Unit,
    onLoginClick: () -> Unit,
    onNavigateToFindPasswordScreen: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    // FocusRequester 인스턴스 생성
    val passwordFocusRequester = remember { FocusRequester() }
    val usernameFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(text = "Login") },
                navigationIcon = {
                    // 뒤로 가기 버튼에 뒤로 가기 기능을 추가합니다.
                    IconButton(onClick = {
                        (context as? Activity)?.onBackPressed()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "뒤로 가기")
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(contentPadding)
                .verticalScroll(scrollState)
                .pointerInput(Unit) {
                    forEachGesture {
                        awaitPointerEventScope {
                            awaitFirstDown().also {
                                keyboardController?.hide()
                            }
                        }
                    }
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_m),
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .heightIn(120.dp)
            )
            Spacer(modifier = Modifier.height(18.dp))
            BLUE_Title()
            Spacer(modifier = Modifier.height(11.dp))
            SubTitle()
            Spacer(modifier = Modifier.height(33.dp))



            UsernameTextField(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .focusRequester(usernameFocusRequester),
                value = username,
                onValueChange = onUsernameChange,
                keyboardActions = KeyboardActions(
                    onNext = {
                        passwordFocusRequester.requestFocus()
                    }
                ),
                imeAction = ImeAction.Next
            )

            PasswordTextField(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .focusRequester(passwordFocusRequester),
                value = password,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = onPasswordChange,
                label = "Password",
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide() // 키보드를 숨깁니다.
                        onLoginClick() // 로그인 처리 함수를 호출합니다.
                    }
                ),
                imeAction = ImeAction.Done
            )

            Spacer(modifier = Modifier.height(30.dp))

            FillButton(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                text = "로그인",
                onClick = onLoginClick
            )
            SignUpBtn(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                text = "회원가입",
                onClick = onNavigateToSignUpScreen
            )
            FindPassword(
                "비밀번호 찾기 >",
                onClick = onNavigateToFindPasswordScreen
            )

        }
    }
}


@Preview
@Composable
private fun LoginScreenPreview() {
    BLURTheme {
        LoginScreen(
            username = "deseruisse",
            password = "mi",
            onUsernameChange = {},
            onPasswordChange = {},
            onNavigateToSignUpScreen = {},
            onLoginClick = {},
            onNavigateToFindPasswordScreen = {},

            )
    }
}