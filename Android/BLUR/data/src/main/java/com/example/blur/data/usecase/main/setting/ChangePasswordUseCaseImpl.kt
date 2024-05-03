package com.example.blur.data.usecase.main.setting

import com.example.blur.data.model.main.setting.ChangePasswordResponse
import com.example.blur.data.retrofit.UserService
import com.example.blur.domain.usecase.main.setting.ChangePasswordUseCase
import java.io.IOException
import javax.inject.Inject

class ChangePasswordUseCaseImpl @Inject constructor(
    private val userService: UserService
) : ChangePasswordUseCase {
    override suspend fun invoke(
        current_password: String,
        new_password: String
    ): Result<String> {
        return try {
            // 서버에 비밀번호 변경 요청을 보냅니다.
            userService.changePassword(current_password = current_password, new_password = new_password)
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
