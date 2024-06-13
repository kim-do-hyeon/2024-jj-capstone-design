import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.blur.blur.presentation.R


@Composable
fun ListItems(
    headlineText: String,
    supportingText: String,
    icon: ImageVector,
    overlineText: String? = null,
    onClick: () -> Unit // 클릭 이벤트 핸들러 추가
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(Modifier.clickable(onClick = onClick)) // 클릭 이벤트 처리
    ){
        ListItem(
            modifier = Modifier,
            headlineContent = {
                Text(headlineText)
            },
            supportingContent = {
                Text(
                    text = supportingText,
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(500),
                        textAlign = TextAlign.Center,
                    )
                )
            },
            leadingContent = {
                Icon(
                    icon,
                    contentDescription = "Localized description",
                )
            },
            overlineContent = overlineText?.let { { Text(text = it) } },
        )
    }
    Divider()
}


@Preview
@Composable
fun ListItemsPreview() {
    ListItems(
        headlineText = "head",
        supportingText = "supp",
        icon = Icons.Filled.List
    ){

    }
}