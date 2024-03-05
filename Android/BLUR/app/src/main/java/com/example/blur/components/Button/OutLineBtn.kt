package com.example.blur.components.Button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedButton
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

@Composable
fun OutLineBtn(value: String,onClick:() -> Unit ={}){
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
    ) {
        Text(
            text = "회원가입",
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = FontFamily(Font(R.font.gmarketsans_bold)),
                fontWeight = FontWeight(700),
                color = Color(0xFF68548E),

                textAlign = TextAlign.Center,
                letterSpacing = 0.04.sp,
            )
        )
    }
}
