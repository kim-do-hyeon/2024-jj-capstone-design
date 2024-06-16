
import ListItems
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blur.blur.presentation.Main.Calendar.CalendarActivity
import com.blur.blur.presentation.Main.Camera.CameraActivity
import com.blur.blur.presentation.Main.RegisterProduct.RegisterProductActivity
import com.blur.blur.presentation.Main.SendMessage.SendMessageActivity
import com.blur.blur.presentation.Main.Widgets.WidgetsActivity

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) {
        context.startActivities(
            arrayOf(Intent(context, CameraActivity::class.java))
        )
    }


    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier,
        ) {
            ListItems(
                headlineText = "얼굴 등록",
                supportingText = "나의 사진을 찍어서 기기에 얼굴을 등록해 보세요",
                icon = Icons.Filled.Person,
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        permissionLauncher.launch(
                            arrayOf(
                                READ_MEDIA_IMAGES,
                                READ_MEDIA_VIDEO,
                                READ_MEDIA_VISUAL_USER_SELECTED
                            )
                        )
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VIDEO))
                    } else {
                        permissionLauncher.launch(arrayOf(READ_EXTERNAL_STORAGE))
                    }
                }
            )
            ListItems(
                headlineText = "일정 관리",
                supportingText = "캘린더에 일정 관리해 보세요",
                icon = Icons.Filled.CalendarMonth,
                onClick = {
                    context.startActivity(Intent(context, CalendarActivity::class.java))
                }
            )
            ListItems(
                headlineText = "메시지 보내기",
                supportingText = "스마트 미러에 메시지를 남겨 보세요",
                icon = Icons.Filled.Message,
                onClick = {
                    context.startActivity(Intent(context, SendMessageActivity::class.java))
                }
            )
            ListItems(
                headlineText = "위젯 설정",
                supportingText = "스마트 미러의 위젯을 설정해 보세요.",
                icon = Icons.Filled.Widgets,
                onClick = {
                    context.startActivity(Intent(context, WidgetsActivity::class.java))
                }
            )
            ListItems(
                headlineText = "기기 등록",
                supportingText = "제품을 등록해 보세요",
                icon = Icons.Filled.DeviceHub,
                onClick = {
                    context.startActivity(Intent(context, RegisterProductActivity::class.java))
                }
            )
        }

    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    MenuScreen()
}
