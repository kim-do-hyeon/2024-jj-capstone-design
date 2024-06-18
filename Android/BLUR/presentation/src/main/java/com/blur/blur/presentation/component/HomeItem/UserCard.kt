import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blur.blur.presentation.Component.ProfileImage

@Composable
fun UserCard(
    originalname: String,
    email: String,
    profileImageUrl: String?,
    onImageChangeClick: () -> Unit,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1.8f),
                contentAlignment = Alignment.Center
            ) {
                Column (
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "좋은 하루 보내세요.",
                        style = MaterialTheme.typography.headlineLarge,
                    )
                    Text(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "${originalname}님",
                        style = MaterialTheme.typography.headlineLarge,
                    )

                    Text(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth(),
                        text = email,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.bodyMedium
                    )

                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Box {
                    ProfileImage(
                        modifier = Modifier.size(120.dp),
                        profileImageUrl = profileImageUrl,
                    )

                    IconButton(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        onClick = onImageChangeClick
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color.Gray,
                                    shape = CircleShape
                                )
                                .background(
                                    color = Color.White,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(20.dp),
                                imageVector = Icons.Outlined.Settings,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun UserCardPreview() {
    UserCard(
        originalname = "test",
        email="test@test.test",
        profileImageUrl = null,
        onImageChangeClick = {})
}