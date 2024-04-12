package com.example.blur.presentation.Login

import OriginalNameTextField
import PasswordTextField
import UsernameTextField
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blur.components.Text.BLUE_Title
import com.example.blur.components.TextField.EmailTextField
import com.example.blur.presentation.R
import com.example.blur.presentation.Component.Button.FillButton
import com.example.blur.presentation.Component.Button.TwoText
import com.example.blur.presentation.theme.BLURTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect


@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onNavigateToLoginScreen: () -> Unit,
    onNavigateToFindPasswordScreen: () -> Unit,
) {
    val state = viewModel.collectAsState().value
    val context = LocalContext.current
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SignUpsideEffect.Toast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                Log.e("SignUpScreen", sideEffect.message)
            }

            SignUpsideEffect.NavigateToLoginScreen -> onNavigateToLoginScreen()
        }
    }

    SignUpScreen(
        originalname = state.originalname,
        email = state.email,
        username = state.username,
        password1 = state.password,
        password2 = state.repeatPassword,

        onOriginalNameChange = viewModel::onOriginalNameChange,
        onEmailChange = viewModel::onEmailChange,
        onUsernameChange = viewModel::onUsernameChange,
        onPassword1Change = viewModel::onPasswordChange,
        onPassword2Change = viewModel::onRepeatPasswordChange,
        onSignUpClick = viewModel::onSignUpClick,
        onNavigateToLoginScreen = onNavigateToLoginScreen,

        showOriginalnameField = state.showOriginalnameField,
        showUsernameField = state.showUsernameField,
        showPasswordField = state.showPasswordField
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(
    originalname: String,
    email: String,
    username: String,
    password1: String,
    password2: String,

    onOriginalNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPassword1Change: (String) -> Unit,
    onPassword2Change: (String) -> Unit,

    onSignUpClick: () -> Unit,
    onNavigateToLoginScreen: () -> Unit,

    showOriginalnameField: Boolean,
    showUsernameField: Boolean,
    showPasswordField: Boolean,
) {
    val scrollState = rememberScrollState()

    val keyboardController = LocalSoftwareKeyboardController.current

    val emailFocusRequester = remember { FocusRequester() }
    val usernameFocusRequester = remember { FocusRequester() }
    val password1FocusRequester = remember { FocusRequester() }
    val password2FocusRequester = remember { FocusRequester() }
    val originalnameFocusRequester = remember { FocusRequester() }

    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "회원가입") },
                navigationIcon = {
                    // 뒤로 가기 버튼에 뒤로 가기 기능을 추가합니다.
                    IconButton(onClick = {
                        (context as? Activity)?.onBackPressed()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "뒤로 가기")
                    }
                }
            )
        },
        content = { contentPadding ->
            Surface {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .padding(contentPadding)
                        .verticalScroll(scrollState)
                ) {
                    BLUE_Title()
                    Text(
                        text = "새로운 계정을 만들어 볼까요?",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 15.sp,
                            lineHeight = 39.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_bold)),
                            fontWeight = FontWeight(400),
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(38.dp))

                    if (showPasswordField) {
                        PasswordTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(password2FocusRequester),
                            value = password2,
                            visualTransformation = PasswordVisualTransformation(),
                            onValueChange = onPassword2Change,
                            label = "RepeatPassword",
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()
                                    onSignUpClick()

                                }),
                            imeAction = ImeAction.Done
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        PasswordTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(password1FocusRequester),
                            value = password1,
                            visualTransformation = PasswordVisualTransformation(),
                            onValueChange = onPassword1Change,
                            label = "Password",
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    password2FocusRequester.requestFocus()
                                }
                            ), // 포커스 이동 순서 수정
                            imeAction = ImeAction.Next
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    if (showUsernameField) {
                        UsernameTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(usernameFocusRequester),
                            value = username,
                            onValueChange = onUsernameChange,
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    if (showPasswordField) password1FocusRequester.requestFocus()
                                    else keyboardController?.hide()
                                }
                            ),
                            imeAction = ImeAction.Next

                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (showOriginalnameField) {
                        OriginalNameTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(originalnameFocusRequester),
                            value = originalname,
                            onValueChange = onOriginalNameChange,
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    if (showUsernameField) usernameFocusRequester.requestFocus()
                                    else keyboardController?.hide()
                                }
                            ),
                            imeAction = ImeAction.Next

                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    EmailTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(emailFocusRequester),
                        value = email,
                        onValueChange = onEmailChange,
                        keyboardActions = KeyboardActions(
                            onNext = {
                                if (showOriginalnameField) originalnameFocusRequester.requestFocus()
                                else keyboardController?.hide()
                            }
                        ),
                        imeAction = ImeAction.Next
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    FillButton(
                        modifier = Modifier
                            .padding(vertical = 24.dp)
                            .fillMaxWidth(),
                        text = "회원가입",
                        onClick = onSignUpClick
                    )

                    TwoText(
                        "계정이 있습니까?",
                        "로그인 >",
                        onClick = onNavigateToLoginScreen
                    )

                }

            }
        }
    )
}


@Preview
@Composable
private fun SignUpScreenPreview() {
    BLURTheme {
        SignUpScreen(
            originalname = "",
            email = "",
            username = "",
            password1 = "",
            password2 = "",

            onOriginalNameChange = {},
            onEmailChange = {},
            onUsernameChange = {},
            onPassword1Change = {},
            onPassword2Change = {},
            onSignUpClick = {},
            onNavigateToLoginScreen = {},

            showOriginalnameField = true,
            showUsernameField = true,
            showPasswordField = true,
        )
    }
}