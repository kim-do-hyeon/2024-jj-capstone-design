@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.blur.blur.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Default
) {
    val passwordRegex = Regex("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#\\\$%^&*()\\-_=+{};:,<.>]).{8,}\$")

    // Password 검사 함수
    fun isValidPassword(password: String): Boolean {
        return passwordRegex.matches(password)
    }

    var password by remember { mutableStateOf(value) }
    val isErrorInPassword by remember {
        derivedStateOf {
            if (password.isEmpty()) {
                false
            } else {
                !isValidPassword(password)
            }
        }
    }

    var isPasswordHidden by rememberSaveable { mutableStateOf(true) }

    Column {
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                onValueChange(it)
            },
            modifier = modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text(text = label) },
            placeholder = { Text(text = "비밀번호를 입력하세요") },
            supportingText = {
                if (isErrorInPassword) {
                    Text(text = "숫자, 영어, 특수기호가 포함 8자 이상이어야 합니다.")
                }
            },
            isError = isErrorInPassword,
            visualTransformation = if (isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = imeAction
            ),
            keyboardActions = keyboardActions,
            trailingIcon = {
                IconButton(
                    onClick = { isPasswordHidden = !isPasswordHidden },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = if (isPasswordHidden) R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24),
                        contentDescription = if (isPasswordHidden) "비밀번호 숨김" else "비밀번호 표시"
                    )
                }
            }
        )
    }
}
