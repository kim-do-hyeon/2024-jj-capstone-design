package com.blur.blur.presentation.Main.Setting.ChangePassword

import PasswordTextField
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blur.blur.presentation.R
import com.blur.blur.presentation.Component.Button.FillButton
import com.blur.blur.presentation.Login.LoginActivity
import com.blur.blur.presentation.Main.Setting.ChangeName.ChangeNameEffect
import com.blur.blur.presentation.SplashActivity
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ChangePasswordScreen(
    viewModel: ChangePasswordViewModel = hiltViewModel(),

    ) {
    val state = viewModel.collectAsState().value
    val context = LocalContext.current
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) { // sideEffect로부터 실제 효과를 비교
            is ChangePasswordEffect.Toast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }

            ChangePasswordEffect.NavigateToLoginActivity -> {
                context.startActivity(
                    Intent(context, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
            }

            ChangePasswordEffect.NavigateToMainActivity -> {
                context.startActivity(
                    Intent(context, SplashActivity::class.java).apply {

                    }
                )
            }

            else -> {}
        }
    }

    ChangePasswordScreen(
        current_password = state.current_password,
        password2 = state.password2,
        password3 = state.password3,
        oncurrent_passwordChange = viewModel::onPassword1Change,
        onPassword2Change = viewModel::onPassword2Change,
        onPassword3Change = viewModel::onPassword3Change,
        onChangeClick = viewModel::onChangeClick,
        onMainScreen = viewModel::onMainScreen
    )
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun ChangePasswordScreen(
    current_password: String,
    password2: String,
    password3: String,
    oncurrent_passwordChange: (String) -> Unit,
    onPassword2Change: (String) -> Unit,
    onPassword3Change: (String) -> Unit,
    onMainScreen: () -> Unit,
    onChangeClick: () -> Unit,
) {
    val text = AnnotatedString("안전한 비밀번호 변경을 위해 현재 비밀번호를 다시 입력해 주세요. 비밀번호가 타인에게 노출되지 않도록 주의해 주세요.")
    val password1FocusRequester = remember { FocusRequester() }
    val password2FocusRequester = remember { FocusRequester() }
    val password3FocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val spanStyle = SpanStyle(
        color = Color.Black, // 텍스트의 색상을 검은색으로 지정
        fontFamily = FontFamily(Font(R.font.roboto_bold)),
        fontWeight = FontWeight(600),
        fontSize = 15.sp,
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(text = "비밀번호 변경") },
                navigationIcon = {
                    IconButton(
                        onClick = onMainScreen
                    ) {
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
                .padding(16.dp)
                .padding(contentPadding),
        ) {
            Box(
                modifier = Modifier
                    .weight(0.2f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = spanStyle) {
                            append(text.substring(0, 11)) // "안전한 비밀번호 변경" 부분
                        }
                        append(text.substring(11)) // 나머지 텍스트
                    },
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp, // 기본 폰트 크기 설정
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentSize()
                )
            }
            Divider()
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 30.dp),
                        text = "현재 비밀번호",
                        style = MaterialTheme.typography.bodySmall
                    )

                    PasswordTextField(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .focusRequester(password1FocusRequester),
                        value = current_password,
                        visualTransformation = PasswordVisualTransformation(),
                        onValueChange = oncurrent_passwordChange,
                        label = "Password",
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide() // 키보드를 숨깁니다.
                            }
                        ),
                        imeAction = ImeAction.Next
                    )

                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp),
                        text = "새 비밀번호",
                        style = MaterialTheme.typography.bodySmall
                    )

                    PasswordTextField(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .focusRequester(password2FocusRequester),
                        value = password2,
                        visualTransformation = PasswordVisualTransformation(),
                        onValueChange = onPassword2Change,
                        label = "Password",
                        keyboardActions = KeyboardActions(
                            onDone = {
                                password1FocusRequester.requestFocus() // 키보드를 숨깁니다.
                            }
                        ),
                        imeAction = ImeAction.Next
                    )

                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp),
                        text = "새 비밀번호 확인",
                        style = MaterialTheme.typography.bodySmall
                    )

                    PasswordTextField(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .focusRequester(password3FocusRequester),
                        value = password3,
                        visualTransformation = PasswordVisualTransformation(),
                        onValueChange = onPassword3Change,
                        label = "Password",
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                onChangeClick()
                            }
                        ),
                        imeAction = ImeAction.Done
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    FillButton(
                        modifier = Modifier
                            .padding(vertical = 24.dp)
                            .fillMaxWidth(),
                        text = "비밀번호변경",
                        onClick = onChangeClick
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}


@Preview
@Composable
fun ChangePasswordScreenPreview() {
    ChangePasswordScreen(
        current_password = "maiorum",
        password2 = "legimus",
        password3 = "accusata",
        oncurrent_passwordChange = {},
        onPassword2Change = {},
        onPassword3Change = {},
        onChangeClick = {},
        onMainScreen = {}
    )
}