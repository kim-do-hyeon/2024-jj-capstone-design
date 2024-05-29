package com.blur.blur.data.usecase.login

import android.content.Context
import com.blur.blur.data.di.SharedPreferencesManager
import com.blur.blur.data.retrofit.UserService
import com.blur.blur.domain.usecase.login.LoginUseCase
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val context: Context, // Context 추가
    private val userService: UserService,
) : LoginUseCase {
    override suspend fun invoke(username: String, password: String): Result<String> =
        kotlin.runCatching {
            val response = userService.login(username = username, password = password)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody?.result == "success") {
                    response.headers()["Set-Cookie"]?.let { cookie ->
                        // SharedPreferences를 사용하여 쿠키 저장
                        SharedPreferencesManager.saveCookie(context, cookie)
                        SharedPreferencesManager.saveUsername(context, username)
                    }
                    // 성공 메시지 반환. responseBody.message가 null이면 "Login successful"을 반환
                    "Login successful"
                } else {
                    throw Exception(responseBody?.message ?: "Unknown error occurred")
                }
            } else {
                throw Exception("HTTP 오류: ${response.code()}")
            }
        }
}
