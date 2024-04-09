package com.example.blur.data.usecase

import com.example.blur.data.retrofit.UserService
import com.example.blur.domain.usecase.login.SignUpUseCase
import java.io.IOException
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(
    private val userService: UserService
) : SignUpUseCase {
    override suspend fun invoke(
        email:String,
        username: String,
        password: String
    ): Result<Boolean> {
        return try {
            // 서버에 회원가입 요청을 보냅니다.
            userService.signup(email = email, username = username, password = password)
            // 만약 서버가 성공적으로 응답했다면 성공 결과를 반환합니다.
            Result.success(true)
        } catch (e: IOException) {
            // IOException이 발생하면 네트워크 관련 문제로 실패한 것으로 간주하고 실패 결과를 반환합니다.
            Result.failure<Boolean>(e)
        } catch (e: Exception) {
            // 그 외의 예외가 발생하면 일반적인 오류로 처리하고 실패 결과를 반환합니다.
            Result.failure<Boolean>(e)
        }
    }
}
