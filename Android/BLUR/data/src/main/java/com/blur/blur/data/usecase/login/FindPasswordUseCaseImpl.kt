package com.blur.blur.data.usecase.login

import com.blur.blur.data.retrofit.UserService
import com.blur.blur.domain.usecase.login.FindPasswordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.Properties
import javax.inject.Inject
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class FindPasswordUseCaseImpl @Inject constructor(
    private val userService: UserService
) : FindPasswordUseCase {
    override suspend fun invoke(username: String, email: String): Result<String> {
        return try {
            // Retrofit 호출을 실행합니다.
            val call: Call<ResponseBody> = userService.findPassword(username, email)
            val response: Response<ResponseBody> = call.execute()

            if (response.isSuccessful) {
                // 응답 본문을 문자열로 변환합니다.
                val responseBodyString = response.body()?.string() ?: ""
                // 문자열을 JSONObject로 파싱합니다.
                val jsonObject = JSONObject(responseBodyString)
                // JSON 객체에서 "message" 키를 사용하여 임시 비밀번호를 추출합니다.
                val tempPassword = jsonObject.getString("message")

                // 임시 비밀번호를 사용하여 이메일을 전송하는 로직을 여기에 구현합니다.
                sendEmail(email, "귀하의 임시 비밀번호는 $tempPassword 입니다.")
                Result.success("임시 비밀번호가 이메일로 성공적으로 전송되었습니다.")
            } else {
                // 요청이 실패한 경우
                Result.failure(IOException("서버로부터 비정상 응답을 받았습니다: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun sendEmail(userEmail: String, message: String) {
        runBlocking {
            launch(Dispatchers.IO) {
                // 발신자 이메일 주소
                val fromEmail = "yeller0828@gmail.com"
                // 발신자 이메일 비밀번호
                val password = "xqsg kewg jgak vsuq"


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
}
