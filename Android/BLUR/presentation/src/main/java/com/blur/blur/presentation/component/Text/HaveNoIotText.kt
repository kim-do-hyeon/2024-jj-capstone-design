package com.blur.blur.components.Text

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun HaveNoIotText(){
    Text(
        text = "BLUR에서 제어 가능한 \n홈 IoT 기기가 없습니다.",
        style = TextStyle(
            fontSize = 22.sp,
            lineHeight = 28.sp,
            fontFamily = FontFamily(Font(R.font.roboto_bold)),
            fontWeight = FontWeight(700),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = "홈 IoT기기를 연결해주세요",
        style = TextStyle(
            fontSize = 20.sp,
            lineHeight = 28.sp,
            fontFamily = FontFamily(Font(R.font.roboto_regular)),
            fontWeight = FontWeight(500),
            color = Color(0xFF838383),
            textAlign = TextAlign.Center,
        )
    )
}

