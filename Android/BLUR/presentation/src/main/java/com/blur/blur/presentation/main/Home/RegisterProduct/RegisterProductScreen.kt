package com.blur.blur.presentation.Main.Home.RegisterProduct

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.blur.blur.components.TextField.AddDeviceTextField
import com.blur.blur.presentation.Component.Button.FillButton
import com.blur.blur.presentation.R
import com.blur.blur.presentation.SplashActivity
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect


@Composable
fun RegisterProductScreen(
    viewModel: RegisterProductViewModel = hiltViewModel(),
){
    val state = viewModel.collectAsState().value
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            RegisterProductSideEffect.NavigateToMainActivity -> {
                context.startActivity(
                    Intent(context, SplashActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
            }

            is RegisterProductSideEffect.Toast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    RegisterProductScreen(
        deviceCode = state.code,
        onDeviceCode = viewModel::onDeviceCode,
        onClick = viewModel::onClick,
    )

}

@Composable
private fun RegisterProductScreen(
    deviceCode:String,
    onDeviceCode:(String) ->Unit,
    onClick:()->Unit

) {
    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "등록하실 제품의 제품 코드를 입력해 주세요",
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 39.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    fontWeight = FontWeight(700),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "제품코드를 입력할때 - 을 빼고 입력해주세요",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 23.4.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF828282),
                    textAlign = TextAlign.Center,
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            AddDeviceTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = deviceCode,
                onValueChange = onDeviceCode,
            )

            FillButton(
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .fillMaxWidth(),
                text = "제품 등록",
                onClick = onClick
            )


            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
fun RegisterProductScreenPreview(){
    RegisterProductScreen()
}