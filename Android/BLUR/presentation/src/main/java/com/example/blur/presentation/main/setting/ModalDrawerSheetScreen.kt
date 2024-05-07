package com.example.blur.presentation.Main.Setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.blur.presentation.Main.Setting.ChangePassword.ChangePasswordActivity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blur.presentation.Component.MenuListItems
import com.example.blur.presentation.Component.ProfileImage
import com.example.blur.presentation.Login.LoginActivity
import com.example.blur.presentation.Main.Setting.ChangeEmail.ChangeEmailActivity
import com.example.blur.presentation.Main.Setting.ChangeName.ChangeNameActivity
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect


@Composable
fun ModalDrawerSheetScreen(
    viewModel: ModalDrawerSheetViewModel = hiltViewModel(),
) {
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
            ModalDrawerSheetSideEffect.NavigateToLoginActivity -> {
                context.startActivity(
                    Intent(context, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
            }

            ModalDrawerSheetSideEffect.NavigateToChangePasswordScreen -> {
                context.startActivity(
                    Intent(context, ChangePasswordActivity::class.java).apply {

                    }
                )
            }

            ModalDrawerSheetSideEffect.NavigateToEmailScreen -> {
                context.startActivity(
                    Intent(context, ChangeEmailActivity::class.java).apply {

                    }
                )
            }

            ModalDrawerSheetSideEffect.NavigateToNameScreen -> {
                context.startActivity(
                    Intent(context, ChangeNameActivity::class.java).apply {

                    }
                )
            }

            ModalDrawerSheetSideEffect.ImageSelectionCancelled -> {
                // 이미지 선택 취소에 대한 토스트 메시지
                Toast.makeText(context, "이미지 선택 취소", Toast.LENGTH_SHORT).show()
            }

            ModalDrawerSheetSideEffect.ImageUploadFailure -> {
                // 이미지 업로드 실패에 대한 토스트 메시지
                Toast.makeText(context, "이미지 업로드 실패.", Toast.LENGTH_SHORT).show()
            }

            ModalDrawerSheetSideEffect.ImageUploadSuccess -> {
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

    // SettingScreen 내부의 로그아웃 버튼을 렌더링
    ModalDrawerSheetScreenContent(
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
        onPasswordChange = viewModel::onPasswordChange,
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        cookie = cookie, // 쿠키 값을 전달
        sessionTestResult = sessionTestResult // sessionTestResult 값을 전달
    )
}

@Composable
fun ModalDrawerSheetScreenContent(
    originalname: String,
    email: String,
    profileImageUrl: String?,
    onLogoutClick: () -> Unit,
    onImageChangeClick: () -> Unit,
    onNameChange: () -> Unit,
    onEmailChange: () -> Unit,
    onPasswordChange: () -> Unit,
    cookie: String?, // 쿠키 값을 매개변수로 받음
    sessionTestResult: String?, // sessionTestResult 값을 매개변수로 받음
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 120.dp)
            .background(MaterialTheme.colorScheme.background)

    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Box {
            ProfileImage(
                modifier = Modifier.size(150.dp),
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

        Text(
            modifier = Modifier
                .padding(top = 20.dp),
            text = originalname,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            modifier = Modifier
                .padding(top = 4.dp),
            text = email,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Divider()
        MenuListItems(
            headlineText = "이름 변경",
            onClick = onNameChange
        )
        MenuListItems(
            headlineText = "이메일 변경",
            onClick = onEmailChange
        )
        MenuListItems(
            headlineText = "비밀번호 변경",
            onClick = onPasswordChange
        )
        MenuListItems(
            headlineText = "로그아웃",
            onClick = onLogoutClick
        )

        Spacer(modifier = Modifier.weight(1f))
        // 쿠키 값 표시
        Text(text = cookie ?: "쿠키가 없습니다.")

        // 세션 테스트 결과 표시
        Spacer(modifier = Modifier.weight(1f))

        Text(text = "Session Test Result: $sessionTestResult")

    }
}

@Preview
@Composable
fun DrawerStateDemoPreview() {
    ModalDrawerSheetScreenContent(
        originalname = "Dale Parks",
        email = "yeobh0828@naver.com",
        profileImageUrl = "",
        onLogoutClick = {},
        onImageChangeClick = {},
        onPasswordChange = {},
        onNameChange = {},
        onEmailChange = {},
        cookie = null,
        sessionTestResult = null
    )
}
