@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.blur.presentation.R
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun OriginalNameTextField(
    modifier: Modifier = Modifier,
    value: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Default
) {
    val originalnameRegex = Regex("^[a-zA-Z가-힣]{1,}\$")

    // originalname 검사 함수
    fun isValidoriginalname(originalname: String): Boolean {
        return originalnameRegex.matches(originalname)
    }

    var originalname by remember { mutableStateOf(value) }

    val isErrorInoriginalname by remember {
        derivedStateOf {
            if (originalname.isEmpty()) {
                false
            } else {
                !isValidoriginalname(originalname)
            }
        }
    }
    Column {
        OutlinedTextField(
            value = originalname,
            onValueChange = {
                originalname = it
                onValueChange(it) // 새로운 값이 입력될 때마다 외부로 알림
            },
            modifier = modifier
                .fillMaxWidth(),
            singleLine = true,
            label = { Text(text = "이름") },
            placeholder = { Text("이름을 입력하세요.") },
            visualTransformation = visualTransformation, // 시각적 변환 적용
            supportingText = {
                if (isErrorInoriginalname) {
                    Text(text = "이름은 영어, 한글만 가능합니다.")
                }
            },

            keyboardOptions = KeyboardOptions(
                imeAction = imeAction
            ),
            keyboardActions = keyboardActions,
            isError = isErrorInoriginalname,
            trailingIcon = {
                if (isErrorInoriginalname) {
                    Spacer(modifier = Modifier.width(8.dp)) // 아이콘과 텍스트 사이의 간격 조정
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_error_24),
                        contentDescription = "에러", // 아이콘 설명
                        tint = Color.Red
                    )
                }
            }
        )
    }
}
