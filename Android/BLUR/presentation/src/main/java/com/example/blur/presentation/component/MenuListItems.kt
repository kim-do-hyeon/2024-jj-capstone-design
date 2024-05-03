package com.example.blur.presentation.Component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
fun MenuListItems(
    modifier: Modifier = Modifier,
    headlineText: String,
    onClick: () -> Unit // 클릭 이벤트 핸들러 추가
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top=10.dp)
            .padding(horizontal = 10.dp)
            .then(Modifier.clickable(onClick = onClick)) // 클릭 이벤트 처리
    ){
        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            headlineContent = {
                Text(
                    text = headlineText,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 22.5.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        fontWeight = FontWeight(500),
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                    )
                )

            }
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Divider()
}



@Preview
@Composable
fun MenuListItemsPreview() {
    MenuListItems(
        headlineText = "head",
    ){

    }
}