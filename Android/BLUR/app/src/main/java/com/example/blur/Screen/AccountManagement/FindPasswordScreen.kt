package com.example.blur

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blur.components.Button.FillBtn
import com.example.blur.components.Button.TwoText
import com.example.blur.components.Text.MessageText
import com.example.blur.components.Text.MessageText2
import com.example.blur.components.TextField.EmailTextField
import com.example.blur.components.TextField.IDTextField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindPasswordScreen(
    navController: NavHostController
) {

    var emailError by remember { mutableStateOf(false) } // 이메일 유효성 검사 결과를 나타내는 변수

    var userEmail by remember { mutableStateOf("") }

    var userID by remember { mutableStateOf("") }
    var userIDError by remember { mutableStateOf(false) }
    var showIDField by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // ID 유효성 검사 함수
    fun isValidID(id: String): Boolean {
        val idRegex = Regex("^[a-zA-Z0-9]{1,}\$")
        return idRegex.matches(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "비밀번호 찾기") }, navigationIcon = {
                IconButton(onClick = { navController.navigate("Login") }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Localized description")
                }
            })
        },
        content = { contentPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 53.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(contentPadding)
            ) {
                Spacer(modifier = Modifier.height(210.dp))

                MessageText(
                    "암호를 잊어버렸습니까?", 28
                )

                Spacer(modifier = Modifier.height(26.dp))

                MessageText2(
                    "걱정하지 마세요! 계정과 연결된 이메일 주소를 \n 입력하시면 메일로 비밀번호를 보내 드립니다.", 15
                )

                Spacer(modifier = Modifier.height(35.dp))

                if (showIDField) {
                    IDTextField(onValueChange = {
                        userID = it
                        userIDError = it.isBlank() || !isValidID(it)
                    })
                }

                Spacer(modifier = Modifier.height(10.dp))

                EmailTextField(onValueChange = {
                    userEmail = it
                    emailError = !Patterns.EMAIL_ADDRESS.matcher(it).matches()
                    if (!emailError && it.isNotBlank()) {
                        // 이메일이 유효하고 비어있지 않다면 ID 필드를 표시합니다.
                        showIDField = true
                    }
                })
                Spacer(modifier = Modifier.height(13.dp))

                FillBtn("메일전송", onClick = {
                    val apiService = RetrofitClient.getApiService()

                    // Retrofit의 enqueue 메서드를 사용하여 백그라운드 스레드에서 작업을 수행
                    apiService.findPassword(username = userID, email = userEmail)
                        .enqueue(object : Callback<ResponseBody> {
                            override fun onResponse(
                                call: Call<ResponseBody>, response: Response<ResponseBody>
                            ) {
                                if (response.isSuccessful) {
                                    val responseBody: ResponseBody? = response.body()
                                    responseBody?.let {
                                        try {
                                            val jsonString = it.string() // ResponseBody를 문자열로 변환
                                            val jsonObject = JSONObject(jsonString)
                                            val message = jsonObject.getString("message")

                                            // 백그라운드에서 이메일 보내기
                                            scope.launch {
                                                sendEmail(userEmail, "임시 비밀번호: $message")
                                            }
                                        } catch (e: Exception) {
                                            // JSON 파싱 실패 시 처리
                                            scope.launch {
                                                snackbarHostState.showSnackbar("응답 데이터 파싱 실패")
                                            }
                                        }
                                    } ?: run {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("응답 데이터가 없습니다.")
                                        }
                                    }
                                } else {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("메일 전송 실패")
                                    }
                                }
                            }

                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                // 통신 실패 처리
                                scope.launch {
                                    snackbarHostState.showSnackbar("통신 실패")
                                }
                            }
                        })
                })

                Spacer(modifier = Modifier.height(202.dp))

                TwoText("비밀번호가 기억났나요?", "로그인", onClick = {
                    navController.navigate("Login")
                })
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        }
    )
}


@Preview
@Composable
fun FindPasswordScreenPreview() {
    FindPasswordScreen(rememberNavController())
}


// 이메일을 보내는 함수
fun sendEmail(userEmail: String, message: String) {
    runBlocking {
        launch(Dispatchers.IO) {
            // 발신자 이메일 주소
            val fromEmail = "yeller0828@gmail.com"
            // 발신자 이메일 비밀번호
            val password = "xqsg kewg jgak vsuq"

            println("이메일을 보내는 중: $userEmail") // 이 부분이 추가된 부분입니다.

            val properties = Properties().apply {
                put("mail.smtp.auth", "true")
                put("mail.smtp.starttls.enable", "true")
                put("mail.smtp.host", "smtp.gmail.com")
                put("mail.smtp.port", "587")
            }

            val session = Session.getInstance(properties, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(fromEmail, password)
                }
            })

            try {
                val mimeMessage = MimeMessage(session).apply {
                    setFrom(InternetAddress(fromEmail))
                    addRecipient(Message.RecipientType.TO, InternetAddress(userEmail))
                    subject = "임시 비밀번호"
                    setText(message)
                }

                Transport.send(mimeMessage)

                println("이메일을 성공적으로 보냈습니다: $userEmail") // 이 부분이 추가된 부분입니다.
            } catch (e: MessagingException) {
                e.printStackTrace()
            }
        }
    }
}