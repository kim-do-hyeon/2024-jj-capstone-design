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
fun SubTitle(){
    Text(
        text = "Beyond Limitations, Unleashing Reflection",
        style = TextStyle(
            fontSize = 12.sp,
            lineHeight = 21.sp,
            fontFamily = FontFamily(Font(R.font.gmarketsans_light)),
            fontWeight = FontWeight(300),
            color = MaterialTheme.colorScheme.onBackground,

            textAlign = TextAlign.Center,
        )
    )
}