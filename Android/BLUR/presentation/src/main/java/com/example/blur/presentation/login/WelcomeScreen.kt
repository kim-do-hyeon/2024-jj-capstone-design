package com.example.blur.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blur.components.Text.BLUE_Title
import com.example.blur.components.Text.SubTitle
import com.example.blur.presentation.R
import com.example.blur.presentation.component.Button.FillButton
import com.example.blur.presentation.component.Button.SignUpBtn
import com.example.blur.presentation.theme.BLURTheme
import com.example.blur.presentation.theme.primaryLight

/**
 * @author soohwan.ok
 */
@Composable
fun WelcomeScreen(
    onNavigateToLoginScreen:()->Unit,
    onNavigateToSignUpScreen:()->Unit
) {
    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.padding(top = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.logo_l),
                    contentDescription = null,
                    modifier = Modifier
                        .width(120.dp)
                        .heightIn(170.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.weight(1f))
                BLUE_Title()
                Spacer(modifier = Modifier.height(11.dp))
                SubTitle()

                Spacer(modifier = Modifier.weight(1f))
                FillButton(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    text = "시작하기",
                    onClick = onNavigateToLoginScreen
                )
                SignUpBtn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    text = "회원가입",
                    onClick = onNavigateToSignUpScreen
                )
            }

        }
    }
}

@Preview
@Composable
private fun WelcomeScreenPreview() {
    BLURTheme {
        WelcomeScreen(
            onNavigateToLoginScreen = {},
            onNavigateToSignUpScreen = {}
        )
    }
}