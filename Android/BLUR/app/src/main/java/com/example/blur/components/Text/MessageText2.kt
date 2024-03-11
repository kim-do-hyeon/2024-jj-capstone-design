package com.example.blur.components.Text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.blur.R

@Composable
fun MessageText2(value:String, Size:Int){
    Text(
        text = value,
        style = TextStyle(
            fontSize = Size.sp,
            lineHeight = 22.5.sp,
            fontFamily = FontFamily(Font(R.font.roboto_bold)),
            fontWeight = FontWeight(500),
            color = Color(0xFFAAB3BE),
            textAlign = TextAlign.Center,
        )
    )
}