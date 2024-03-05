package com.example.blur.components.Button

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.blur.R

@Composable
fun FindPassword(value: String, onClick: () -> Unit = {}) {
    TextButton(
        onClick = onClick
    ) {
        Text(
            text = value,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = FontFamily(Font(R.font.gmarketsans_bold)),
                fontWeight = FontWeight(400),
                color = Color(0xFF68548E),

                textAlign = TextAlign.Center,
                letterSpacing = 0.04.sp,
            )
        )
    }
}