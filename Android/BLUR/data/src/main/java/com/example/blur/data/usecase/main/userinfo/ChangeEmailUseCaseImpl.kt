package com.example.blur.data.usecase.main.userinfo

import com.example.blur.data.retrofit.UserService
import com.example.blur.domain.usecase.main.userinfo.ChangeEmailUseCase
import javax.inject.Inject
import retrofit2.await
import java.io.IOException

class ChangeEmailUseCaseImpl @Inject constructor(
    private val userService: UserService
) : ChangeEmailUseCase {
    override suspend fun invoke(type: String, email: String): Result<String> {
        return try {
            // 서버에 이메일 변경 요청을 보냅니다.
            userService.changeEmail(type = type, email = email)
            Result.success("Success")
        } catch (e: IOException) {
            // IOException이 발생하면 네트워크 관련 문제로 실패한 것으로 간주하고 실패 결과를 반환합니다.
            Result.failure<String>(e)
        } catch (e: Exception) {
            // 그 외의 예외가 발생하면 일반적인 오류로 처리하고 실패 결과를 반환합니다.
            Result.failure<String>(e)
        }
    }
}