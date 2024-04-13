package com.example.blur.presentation.Component

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blur.presentation.R


@Composable
fun ChatBubble(
    content:String,
    time:String,
) {

    Row (
        modifier = Modifier
            .padding(8.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement =  Arrangement.End,
        verticalAlignment = Alignment.Bottom,
    ){
        Text(text =time,
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                fontWeight = FontWeight(700),
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        MessageBox(message = content)

    }
}


@Composable
fun MessageBox(
    message: String,
) {
    // optional 채팅이 길어질 경우 화면의 최대 2/3 를 차지 하도록
    val maxWidthDp = LocalConfiguration.current.screenWidthDp.dp * 2 / 3

    Box(
        modifier = Modifier
            .widthIn(max = maxWidthDp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = message,
            modifier = Modifier.padding(all = 4.dp),
            style = TextStyle(
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                fontWeight = FontWeight(700),
            )
        )
    }
}


@Preview
@Composable
fun ChatBubblePreview(){
    ChatBubble(content = "altera", time = "ultrices")
}

