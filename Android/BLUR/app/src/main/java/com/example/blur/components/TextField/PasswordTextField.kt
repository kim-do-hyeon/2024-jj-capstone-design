package com.example.blur.components.TextField

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.blur.R


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PasswordTextField(
    label: String,
    errorMsg: String = "숫자, 영어, 특수기호가 포함 8자 이상이어야 합니다.",
    onValueChange: (String) -> Unit
) {
    val idRegex = Regex("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#\\\$%^&*()\\-_=+{};:,<.>]).{8,}\$")

    // ID 검사 함수
    fun isValidPassword(password: String): Boolean {
        return idRegex.matches(password)
    }

    var userPassword by remember { mutableStateOf("") }
    val isErrorInUserPassword by remember {
        derivedStateOf {
            if (userPassword.isEmpty()) {
                false
            } else {
                !isValidPassword(userPassword)
            }
        }
    }

    var InputPasswordHidden by rememberSaveable { mutableStateOf(true) }

    Column {
        OutlinedTextField(
            value = userPassword,
            onValueChange = {
                userPassword = it
                onValueChange(it)
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text(text = label) },
            placeholder = { Text(text = "비밀번호를 입력하세요") },
            supportingText = {
                if (isErrorInUserPassword) {
                    Text(text = errorMsg)
                }
            },
            isError = isErrorInUserPassword,
            visualTransformation = if (InputPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(
                    onClick = { InputPasswordHidden = !InputPasswordHidden },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(
                        painter = if (InputPasswordHidden) painterResource(id = R.drawable.baseline_visibility_off_24) else painterResource(
                            id = R.drawable.baseline_visibility_24
                        ),
                        contentDescription = if (InputPasswordHidden) "비밀번호 숨김" else "비밀번호 표시"
                    )
                }
            }
        )
    }
}



