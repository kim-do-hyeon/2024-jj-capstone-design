package com.blur.blur.presentation.Main

import UserCard
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blur.blur.presentation.Login.LoginActivity
import com.blur.blur.presentation.Main.Setting.ChangeEmail.ChangeEmailActivity
import com.blur.blur.presentation.Main.Setting.ChangeName.ChangeNameActivity
import com.blur.blur.presentation.Main.Setting.ChangePassword.ChangePasswordActivity
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val state = viewModel.collectAsState().value
    val cookie by viewModel.cookie.collectAsState()
    val sessionTestResult by viewModel.sessionTestResult.collectAsState()
    val userInfo by viewModel.userInfo.collectAsState()
    val originalname = userInfo?.originalname ?: "Loading..."
    val email = userInfo?.email ?: "Loading..."
    val profileImageUrl = userInfo?.profile_image ?: "Loading..."

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            MainScreenSideEffect.NavigateToLoginActivity -> {
                context.startActivity(
                    Intent(context, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
            }

            MainScreenSideEffect.NavigateToChangePasswordScreen -> {
                context.startActivity(
                    Intent(context, ChangePasswordActivity::class.java).apply {

                    }
                )
            }

            MainScreenSideEffect.NavigateToEmailScreen -> {
                context.startActivity(
                    Intent(context, ChangeEmailActivity::class.java).apply {

                    }
                )
            }

            MainScreenSideEffect.NavigateToNameScreen -> {
                context.startActivity(
                    Intent(context, ChangeNameActivity::class.java).apply {

                    }
                )
            }

            MainScreenSideEffect.ImageSelectionCancelled -> {
                // 이미지 선택 취소에 대한 토스트 메시지
                Toast.makeText(context, "이미지 선택 취소", Toast.LENGTH_SHORT).show()
            }

            MainScreenSideEffect.ImageUploadFailure -> {
                // 이미지 업로드 실패에 대한 토스트 메시지
                Toast.makeText(context, "이미지 업로드 실패.", Toast.LENGTH_SHORT).show()
            }

            MainScreenSideEffect.ImageUploadSuccess -> {
                // 이미지 업로드 성공에 대한 토스트 메시지
                Toast.makeText(context, "이미지 업로드 성공.", Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    // visualMediaPickerLauncher는 사용자가 미디어(이 경우 이미지나 비디오)를 선택했을 때 결과를 처리하는 런처입니다.
    val visualMediaPickerLauncher = rememberLauncherForActivityResult(
        // ActivityResultContracts.PickVisualMedia()는 사용자에게 미디어를 선택하도록 요청하는 계약(contract)을 설정합니다.
        // 이 계약을 통해 사용자는 갤러리나 다른 미디어 저장소에서 파일을 선택할 수 있습니다.
        contract = ActivityResultContracts.PickVisualMedia(),
        // onResult 람다 함수는 사용자가 미디어를 선택하고 나서 실행됩니다.
        // uri: Uri? 파라미터는 선택된 미디어 파일의 URI를 제공합니다. URI가 null이면 사용자가 선택을 취소했음을 의미합니다.
        onResult = { uri: Uri? ->
            // ViewModel의 onImageChange 메소드를 호출하여 선택된 이미지를 처리합니다.
            // 여기서 uri가 null일 수 있으므로 onImageChange 메소드 내에서 null 처리를 적절히 해주어야 합니다.
            viewModel.onImageChange(uri)
        }
    )


    MainScreen(
        profileImageUrl = profileImageUrl,
        originalname = originalname,
        email = email,
        onImageChangeClick = {
            // visualMediaPickerLauncher를 사용하여 이미지만 선택할 수 있는 미디어 선택기를 실행합니다.
            // ActivityResultContracts.PickVisualMedia.ImageOnly를 사용함으로써,
            // 사용자가 선택할 수 있는 미디어 타입을 이미지로 제한합니다.
            visualMediaPickerLauncher.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        },
        onLogoutClick = viewModel::onLogoutClick,
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    originalname: String,
    email: String,
    profileImageUrl: String?,
    onImageChangeClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    val context = LocalContext.current
    Surface {
        Scaffold(
            content = { contentPadding ->
                LazyColumn(
                    modifier = androidx.compose.ui.Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .padding(contentPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    item {
                        UserCard(
                            originalname = originalname,
                            email = email,
                            profileImageUrl = profileImageUrl,
                            onImageChangeClick = onImageChangeClick,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        MenuScreen()
                        Spacer(modifier = Modifier.height(16.dp))
                        ElevatedButton(
                            onClick = onLogoutClick,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(text = "로그아웃")
                            
                        }
                    }
                }
            }
        )
    }

}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(
        originalname = "test",
        email = "test",
        profileImageUrl = null,
        onImageChangeClick = {},
        onLogoutClick = {}
    )
}