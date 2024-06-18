package com.blur.blur.presentation.Component.Text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.blur.blur.presentation.R

@Composable
fun HomeTopText(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "기기",
            style = TextStyle(
                fontSize = 32.sp,
                lineHeight = 20.sp,
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                fontWeight = FontWeight(500),
                color = MaterialTheme.colorScheme.onBackground,
                letterSpacing = 0.08.sp,
            ),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        TextButton(
            onClick = {},
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = "편집",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.04.sp
                )
            )
            Text(
                text = ">",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    fontWeight = FontWeight(700),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.04.sp
                )
            )
        }
    }
}

@Preview
@Composable
fun HomeTopTextPreview(){
    HomeTopText()
}