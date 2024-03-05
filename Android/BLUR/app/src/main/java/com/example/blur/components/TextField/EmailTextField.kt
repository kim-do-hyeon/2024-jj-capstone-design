package com.example.blur.components.TextField

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.blur.R

@Composable
fun EmailTextField(onValueChange: (String) -> Unit) {
    // Text Field
    var UserEmail by remember { mutableStateOf("") }
    val isErrorInUserEmail by remember {
        derivedStateOf {
            if (UserEmail.isEmpty()) {
                false
            } else {
                Patterns.EMAIL_ADDRESS.matcher(UserEmail).matches().not()
            }
        }
    }

    Column {
        OutlinedTextField(

            value = UserEmail,

            onValueChange = {
                UserEmail = it
                onValueChange(it)
            },

            singleLine = true,

            label = { Text(text = "E-Mail") },

            placeholder = { Text("이메일을 입력하세요.") },

            supportingText = {
                if (isErrorInUserEmail) {
                    Text(text = "올바른 이메일을 입력하세요.")
                }
            },

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),

            isError = isErrorInUserEmail,

            modifier = Modifier.fillMaxWidth(),

            trailingIcon = {
                if (isErrorInUserEmail) {
                    Spacer(modifier = Modifier.width(8.dp)) // 아이콘과 텍스트 사이의 간격 조정
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_error_24),
                        contentDescription = "에러", // 아이콘 설명
                        tint = Color.Red
                    )
                }
            }
        )

//        if (isErrorInUserEmail) {
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                text = errorMsg,
//                style = TextStyle(color = Color.Red),
//                modifier = Modifier.padding(start = 16.dp) // 필요에 따라 여백 조정
//            )
//        }
    }
}