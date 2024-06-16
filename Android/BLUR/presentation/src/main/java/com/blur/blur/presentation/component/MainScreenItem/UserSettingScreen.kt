import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blur.blur.presentation.Main.Setting.ChangeEmail.ChangeEmailActivity
import com.blur.blur.presentation.Main.Setting.ChangeName.ChangeNameActivity
import com.blur.blur.presentation.Main.Setting.ChangePassword.ChangePasswordActivity

@Composable
fun UserSettingScreen(

) {
    val context = LocalContext.current
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
    ) {
        Column() {
            ListItems(
                headlineText = "이름 변경",
                supportingText = "회원님의 이름을 변경해 보세요",
                icon = Icons.Filled.AssignmentInd,
                onClick = {
                    context.startActivity(Intent(context, ChangeNameActivity::class.java))
                }
            )
            ListItems(
                headlineText = "이메일 변경",
                supportingText = "회원님의 이메일을 변경해 보세요",
                icon = Icons.Filled.Email,
                onClick = {
                    context.startActivity(Intent(context, ChangeEmailActivity::class.java))
                }
            )
            ListItems(
                headlineText = "비밀번호 변경",
                supportingText = "회원님의 비밀번호를 변경해 보세요",
                icon = Icons.Filled.Password,
                onClick = {
                    context.startActivity(Intent(context, ChangePasswordActivity::class.java))
                }
            )
        }
    }
}

@Preview
@Composable
fun Main_DialogScreenPreview() {
    UserSettingScreen()
}