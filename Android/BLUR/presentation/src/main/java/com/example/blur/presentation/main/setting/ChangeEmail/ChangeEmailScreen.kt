@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.blur.presentation.Main.Setting.ChangeEmail

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blur.components.TextField.EmailTextField
import com.example.blur.presentation.Component.Button.FillButton
import com.example.blur.presentation.Main.MainActivity
import com.example.blur.presentation.R
import com.example.blur.presentation.SplashActivity
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ChangeEmailScreen(
    viewModel: ChangeEmailViewModel = hiltViewModel(),
) {
    val state = viewModel.collectAsState().value
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            ChangeEmailEffect.NavigateToMainActivity -> {
                context.startActivity(
                    Intent(context, SplashActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
            }

            is ChangeEmailEffect.Toast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                Log.e("ChangeEmailScreen", sideEffect.message) // Log 태그 수정
            }

            else -> {}
        }


    }

    ChangeEmailScreen(
        email = state.email,
        onEmailChange = viewModel::onEmailChange,
        onChangeClick = viewModel::onChangeClick,
        onMainScreen = viewModel::onMainScreen
    )
}


@Composable
fun ChangeEmailScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    onChangeClick: () -> Unit,
    onMainScreen: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(text = "Login") },
                navigationIcon = {
                    IconButton(
                        onClick = onMainScreen
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "뒤로 가기")
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(contentPadding),
        ) {
            Surface {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "변경하실 이메일을 입력해 주세요",
                        style = TextStyle(
                            fontSize = 26.sp,
                            lineHeight = 39.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_bold)),
                            fontWeight = FontWeight(700),
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "하단에 변경할 이메일을 입력해주세요",
                        style = TextStyle(
                            fontSize = 18.sp,
                            lineHeight = 23.4.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF828282),
                            textAlign = TextAlign.Center,
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    EmailTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = email,
                        onValueChange = onEmailChange,
                    )

                    FillButton(
                        modifier = Modifier
                            .padding(vertical = 24.dp)
                            .fillMaxWidth(),
                        text = "변경하기",
                        onClick = onChangeClick
                    )


                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview
@Composable
fun ChangeEmailScreenPreview() {
    ChangeEmailScreen(
        email = "ian.soto@example.com",
        onEmailChange = {},
        onChangeClick = {},
        onMainScreen = {}
    )
}