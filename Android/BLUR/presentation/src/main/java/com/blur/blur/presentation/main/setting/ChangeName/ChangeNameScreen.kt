package com.blur.blur.presentation.Main.Setting.ChangeName

import OriginalNameTextField
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
import com.blur.blur.presentation.Component.Button.FillButton
import com.blur.blur.presentation.R
import com.blur.blur.presentation.SplashActivity
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ChangeNameScreen(
    viewModel: ChangeNameViewModel = hiltViewModel(),
){
    val state = viewModel.collectAsState().value
    val context = LocalContext.current

    viewModel.collectSideEffect {sideEffect ->
        when(sideEffect){
            ChangeNameEffect.NavigateToMainActivity -> {
                context.startActivity(
                    Intent(context, SplashActivity::class.java).apply {

                    }
                )
            }
            is ChangeNameEffect.Toast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    ChangenameScreen(
        name = state.name,
        onnameChange = viewModel::onnameChange,
        onChangeClick = viewModel::onChangeClick,

        )

}


@Composable
fun ChangenameScreen(
    name: String,
    onnameChange: (String) -> Unit,
    onChangeClick: ()->Unit,
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
                text = "변경하실 이름을 입력해 주세요",
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
                text = "하단에 변경하실 이름을 입력해주세요",
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

            OriginalNameTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = name,
                onValueChange = onnameChange,
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

@Preview
@Composable
fun ChangenameScreenPreview(){
    ChangenameScreen(
        name = "ian.soto@example.com",
        onnameChange = {},
        onChangeClick = {},
    )
}