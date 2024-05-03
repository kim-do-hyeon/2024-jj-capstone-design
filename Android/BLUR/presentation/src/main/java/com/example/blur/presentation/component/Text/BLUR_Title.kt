package com.example.blur.components.Text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.blur.presentation.R
import com.example.blur.presentation.theme.primaryLight
@Composable
fun BLUE_Title(){
    Text(
        text = "BLUR",
        style = TextStyle(
            fontSize = 62.sp,
            fontFamily = FontFamily(Font(R.font.gmarketsans_bold)),
            fontWeight = FontWeight(700),
            color= primaryLight,
            textAlign = TextAlign.End,
        )
    )
}