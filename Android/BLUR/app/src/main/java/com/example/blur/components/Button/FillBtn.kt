package com.example.blur.components.Button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.blur.R
import com.example.blur.ui.theme.primaryLight

@Composable
fun FillBtn(
    value: String,
    onClick: () -> Unit = {},
    enabled: Boolean = true // enabled 매개변수 추가
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = primaryLight
        ),
        onClick = onClick,
        enabled = enabled, // enabled 속성 추가
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
    ) {
        Text(
            text = value,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = FontFamily(Font(R.font.gmarketsans_bold)),
                fontWeight = FontWeight(700),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
                letterSpacing = 0.04.sp,
            )
        )
    }
}