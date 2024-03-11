package com.example.blur.components.Text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.blur.R

@Composable
fun MessageText(value: String,  size: Int){
    Text(
        text = value,
        style = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = size.sp,
            fontFamily = FontFamily(Font(R.font.roboto_bold)),
            fontWeight = FontWeight(700),
            textAlign = TextAlign.Center
        )
    )
}