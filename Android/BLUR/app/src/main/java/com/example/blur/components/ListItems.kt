package com.example.blur.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.blur.R
import java.time.format.TextStyle

@Composable
fun ListItems(
    headlineText: String,
    supportingText: String,
    icon: ImageVector,
    overlineText: String? = null, // overlineText는 선택적으로 설정할 수 있도록 합니다.
    onClick: () -> Unit // 클릭 이벤트를 위한 람다 함수 추가
) {
    ListItem(
        headlineContent = { Text(headlineText) },
        supportingContent = {
            Text(
                text = supportingText,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Center,
                )
            ) },
        leadingContent = {
            Icon(
                icon,
                contentDescription = "Localized description",
            )
        },
        overlineContent = overlineText?.let { { Text(text = it) } }, // overlineText가 null이 아닐 때만 overlineContent를 설정합니다.
        modifier = Modifier.clickable(onClick = onClick) // 클릭 이벤트 처리
    )
}