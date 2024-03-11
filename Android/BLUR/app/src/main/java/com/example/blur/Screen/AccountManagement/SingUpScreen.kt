package com.example.blur


import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blur.components.Text.BLUE_Title
import com.example.blur.components.Button.FillBtn
import com.example.blur.components.TextField.IDTextField
import com.example.blur.components.Button.TwoText
import com.example.blur.components.TextField.EmailTextField
import com.example.blur.components.TextField.PasswordTextField
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingUpScreen(
    navController: NavHostController,
) {
    var userEmail by remember { mutableStateOf("") }
    var userID by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf(false) } // 이메일 유효성 검사 결과를 나타내는 변수
    var userIDError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) } // 비밀번호 유효성 검사 결과를 나타내는 변수

    var showIDField by remember { mutableStateOf(false) }
    var showPasswordField by remember { mutableStateOf(false) }
    var showconfirmPasswordField by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // ID 유효성 검사 함수
    fun isValidID(id: String): Boolean {
        val idRegex = Regex("^[a-zA-Z0-9]{1,}\$")
        return idRegex.matches(id)
    }

    //Password 유효성 검사 함수
    fun isValidPassword(password: String): Boolean {
        val passwordRegex =
            Regex("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#\\\$%^&*()\\-_=+{};:,<.>]).{8,}\$")
        return passwordRegex.matches(password)
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },

        topBar = {
            TopAppBar(
                title = { Text(text = "회원가입") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("Login") }
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Localized description")
                    }
                }
            )
        },
        content = { contentPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 53.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(contentPadding)
            ) {
                BLUE_Title()

                Spacer(modifier = Modifier.height(31.dp))

                Text(
                    text = "새로운 계정을\n만들어 볼까요?",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 30.sp,
                        lineHeight = 39.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        fontWeight = FontWeight(400),
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(38.dp))

                if(showconfirmPasswordField){
                    PasswordTextField(
                        label = "Confirm Password",
                        onValueChange = {
                            confirmPassword = it
                            passwordError = !isValidPassword(it)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                if (showPasswordField) {
                    PasswordTextField(
                        label = "Password",
                        onValueChange = {
                            password = it
                            passwordError = !isValidPassword(it)
                            if (!passwordError && it.isNotBlank()) {
                                // 아이디가 유효하고 비어있지 않다면 패스워드 필드를 표시합니다.
                                showconfirmPasswordField= true
                            } else {
                                // 아이디 필드가 비어있거나 유효하지 않으면 패스워드 필드를 숨깁니다.
                                showconfirmPasswordField = false
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                if (showIDField) {
                    IDTextField(
                        onValueChange = {
                            userID = it
                            userIDError = it.isBlank() || !isValidID(it)
                            if (!userIDError && it.isNotBlank()) {
                                // 아이디가 유효하고 비어있지 않다면 패스워드 필드를 표시합니다.
                                showPasswordField = true
                            } else {
                                // 아이디 필드가 비어있거나 유효하지 않으면 패스워드 필드를 숨깁니다.
                                showPasswordField = false
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                EmailTextField(
                    onValueChange = {
                        userEmail = it
                        emailError = !Patterns.EMAIL_ADDRESS.matcher(it).matches()
                        if (!emailError && it.isNotBlank()) {
                            // 이메일이 유효하고 비어있지 않다면 ID 필드를 표시합니다.
                            showIDField = true
                        }
                    }
                )

                Spacer(modifier = Modifier.height(30.dp))

                FillBtn(
                    "회원가입",
                    onClick = {
                        val allFieldsFilled =
                            userEmail.isNotBlank() && userID.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
                        val passwordsMatch = password == confirmPassword


                        if (allFieldsFilled && passwordsMatch && !emailError && !userIDError && !passwordError) {

                            val apiService = RetrofitClient.getApiService()

                            apiService.registerUser(username = userID, password = password, email = userEmail)
                                .enqueue(object : Callback<Void> {
                                    override fun onResponse(
                                        call: Call<Void>,
                                        response: Response<Void>
                                    ) {
                                        if (response.isSuccessful) {
                                            // 회원가입 성공 처리
                                            navController.navigate("SingUpComplete")
                                            Log.d("API요청 성공", "회원가입 성공")
                                        } else {
                                            // 회원가입 실패 처리
                                            scope.launch {
                                                snackbarHostState.showSnackbar("회원가입 실패")
                                            }
                                        }
                                    }

                                    override fun onFailure(call: Call<Void>, t: Throwable) {
                                        // 통신 실패 처리
                                        scope.launch {
                                            snackbarHostState.showSnackbar("통신 실패")
                                        }
                                    }
                                })

                        } else {
                            // 필수 입력 필드가 비어 있거나 비밀번호가 일치하지 않는 경우 사용자에게 메시지를 표시할 수 있습니다.
                            if (!allFieldsFilled) {
                                // 필수 입력 필드가 비어 있는 경우
                                scope.launch {
                                    snackbarHostState.showSnackbar("모든 필드를 입력해주세요.")
                                }
                            } else if (!passwordsMatch) {
                                // 비밀번호가 일치하지 않는 경우
                                scope.launch {
                                    snackbarHostState.showSnackbar("비밀번호가 일치하지 않습니다.")
                                }
                            } else if (emailError) {
                                // 이메일 유효성 검사 실패
                                scope.launch {
                                    snackbarHostState.showSnackbar("유효한 이메일을 입력하세요.")
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

                TwoText(
                    "계정이 있습니까?",
                    "로그인 >",
                    onClick = { navController.navigate("Login") }
                )
                Spacer(modifier = Modifier.height(26.dp))
            }
        },

        )
}


@Preview
@Composable
fun SingUpScreenPrevicew() {
    SingUpScreen(rememberNavController())
}