package com.blur.blur.presentation.Component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blur.blur.presentation.R
import com.blur.blur.presentation.theme.primaryLight

@Composable
fun WidgetBox(
    widgetName: String?, // widgetName의 타입을 지정해주어야 합니다.
    onClick:() -> Unit // 다이얼로그를 열기 위한 클릭 핸들러
) {

    Box(
        modifier = Modifier.run {
            size(80.dp) // 클릭 핸들러를 전달합니다.
                .clickable{onClick()}
                .padding(8.dp)
        }, // 외부 패딩을 일정하게 설정
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (widgetName != null) {
                Text(
                    text = widgetName,
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        fontWeight = FontWeight(700),
                        color = primaryLight,
                        textAlign = TextAlign.End
                    )
                )
            } else {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp),
                )
            }
        }
    }
}