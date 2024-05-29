@file:OptIn(ExperimentalMaterial3Api::class)

package com.blur.blur.presentation.Login

import UsernameTextField
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blur.blur.components.Text.MessageText2
import com.blur.blur.components.TextField.EmailTextField
import com.blur.blur.presentation.Component.Button.FillButton
import com.blur.blur.presentation.Component.Text.MessageText
import com.blur.blur.presentation.theme.BLURTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun FindPasswordScreen(
    viewModel: FindPasswordViewModel = hiltViewModel(),

    ) {
    val state = viewModel.collectAsState().value
    val context = LocalContext.current
    viewModel.collectSideEffect {

    }

    FindPasswordScreen(
        email = state.email,
        username = state.username,

        onEmailChange = viewModel::onEmailChange,
        onUsernameChange = viewModel::onUsernameChange,

        onFindPasswordClick = viewModel::onFindPasswordClick,

        showUsernameField = state.showUsernameField,


        )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun FindPasswordScreen(
    email: String,
    username: String,

    onEmailChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,

    onFindPasswordClick: () -> Unit,

    showUsernameField: Boolean,
) {
    // FocusRequester 인스턴스 생성
    val emailFocusRequester = remember { FocusRequester() }
    val usernameFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(text = "비밀번호찾기") },
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
                .verticalScroll(scrollState).pointerInput(Unit) {
                    forEachGesture {
                        awaitPointerEventScope {
                            awaitFirstDown().also {
                                keyboardController?.hide()
                            }
                        }
                    }
                }
        ) {
            Spacer(modifier = Modifier.weight(1f))

            MessageText(
                value = "암호를 잊어버렸습니까?",
                size = 28
            )

            Spacer(modifier = Modifier.height(26.dp))

            MessageText2(
                value = "걱정하지 마세요! 계정과 연결된 이메일 주소를 \n 입력하시면 메일로 비밀번호를 보내 드립니다.",
                size = 15
            )

            Spacer(modifier = Modifier.height(35.dp))

            if (showUsernameField) {
                UsernameTextField(
                    value = username,
                    onValueChange = onUsernameChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(usernameFocusRequester),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide() // 키보드를 숨깁니다.
                            onFindPasswordClick() // 비밀번호 찾기 동작을 실행합니다.
                        }
                    ),
                    imeAction = ImeAction.Done
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
                        // 포커스 로직 수정
                        if (showUsernameField) usernameFocusRequester.requestFocus()
                        else keyboardController?.hide()
                    }
                ),
                imeAction = ImeAction.Next
            )

            FillButton(
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .fillMaxWidth(),
                text = "비밀번호 찾기",
                onClick = onFindPasswordClick
            )

            Spacer(modifier = Modifier.weight(1f))
        }


    }
}

@Preview
@Composable
fun FindPasswordScreenPreview() {
    BLURTheme {
        FindPasswordScreen(
            email = "",
            username = "",
            onEmailChange = {},
            onUsernameChange = {},
            showUsernameField = true,
            onFindPasswordClick = {},
        )
    }
}