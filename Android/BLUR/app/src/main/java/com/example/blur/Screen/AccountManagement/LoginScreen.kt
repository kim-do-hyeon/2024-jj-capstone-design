package com.example.blur

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blur.components.Text.BLUE_Title
import com.example.blur.components.Button.FillBtn
import com.example.blur.components.Button.FindPassword
import com.example.blur.components.TextField.IDTextField
import com.example.blur.components.Button.OutLineBtn
import com.example.blur.components.Text.SubTitle
import com.example.blur.components.TextField.PasswordTextField
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    var userID by remember { mutableStateOf("") }
    var userIDError by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Login") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate("Start")
                        }
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Localized description")
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 53.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(contentPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_m),
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .heightIn(120.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))
            BLUE_Title()
            Spacer(modifier = Modifier.height(11.dp))
            SubTitle()
            Spacer(modifier = Modifier.height(33.dp))
            IDTextField(
                onValueChange = {
                    userID = it
                    userIDError = it.isBlank()
                }
            )

            PasswordTextField(
                label = "Password",
                onValueChange = {
                    password = it
                    passwordError = it.isBlank()
                }
            )

            Spacer(modifier = Modifier.height(30.dp))

            FillBtn(
                "로그인",
                onClick = {
                    val allFieldsFilled = userID.isNotBlank() && password.isNotBlank()

                    if (allFieldsFilled && !userIDError && !passwordError) {

                        val apiService = RetrofitClient.getApiService()

                        apiService.loginUser(username = userID, password = password)
                            .enqueue(object : Callback<ApiService.LoginResponse> {
                                override fun onResponse(
                                    call: Call<ApiService.LoginResponse>,
                                    response: Response<ApiService.LoginResponse>
                                ) {
                                    if (response.isSuccessful) {
                                        val loginResponse = response.body()
                                        if (loginResponse?.result == "success") {
                                            navController.navigate("Home")
                                        } else {
                                            scope.launch {
                                                snackbarHostState.showSnackbar("로그인 실패: ${loginResponse?.message ?: "알 수 없는 오류"}")
                                            }
                                        }
                                    } else {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("서버 응답 실패")
                                        }
                                    }
                                }

                                override fun onFailure(call: Call<ApiService.LoginResponse>, t: Throwable) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("통신 실패")
                                    }
                                }
                            })
                    } else {
                        if (!allFieldsFilled) {
                            scope.launch {
                                snackbarHostState.showSnackbar("모든 필드를 입력해주세요.")
                            }
                        } else if (userIDError) {
                            scope.launch {
                                snackbarHostState.showSnackbar("유효한 ID를 입력하세요.")
                            }
                        } else if (passwordError) {
                            scope.launch {
                                snackbarHostState.showSnackbar("비밀번호를 확인해주세요.")
                            }
                        }
                    }
                },
                enabled = true
            )


            Spacer(modifier = Modifier.height(16.dp))
            OutLineBtn(
                "회원가입",
                onClick = { navController.navigate("SingUp") }
            )

            Spacer(modifier = Modifier.height(16.dp))
            FindPassword(
                "비밀번호 찾기 >",
                onClick = { navController.navigate("FindPassword") }
            )

            Spacer(modifier = Modifier.height(26.dp))
        }
    }
}

@Preview
@Composable
fun LoginScreenPrevicew() {
    LoginScreen(rememberNavController())
}
