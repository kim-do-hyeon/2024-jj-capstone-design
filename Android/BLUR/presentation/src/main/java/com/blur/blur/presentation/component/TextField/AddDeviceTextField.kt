@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.blur.blur.components.TextField

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import com.blur.blur.presentation.R


@Composable
fun AddDeviceTextField(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    var deviceId by rememberSaveable { mutableStateOf(value) }

    // 올바른 형식의 제품 코드 여부 확인
    val isValidDeviceId = remember(deviceId) {
        deviceId.matches(Regex("^[A-Z0-9]{1,8}$"))
    }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = deviceId,
            onValueChange = {
                if (it.length <= 8) {
                    deviceId = it.uppercase() // 입력된 값 대문자로 변환
                    onValueChange(deviceId)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = { Text(text = "제품 코드") },
            placeholder = { Text("제품 코드를 입력하세요") },
            isError = deviceId.isNotBlank() && !isValidDeviceId, // 제품 코드가 비어 있지 않고 유효하지 않은 경우에만 에러 표시
            trailingIcon = {
                if (deviceId.isNotBlank() && !isValidDeviceId) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_error_24),
                        contentDescription = "에러",
                        tint = Color.Red
                    )
                }
            },
            visualTransformation = VisualTransformation.None,
            keyboardActions = KeyboardActions.Default
        )
    }
}
