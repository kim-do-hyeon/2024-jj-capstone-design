package com.blur.blur.data.usecase.main.userinfo

import com.blur.blur.data.retrofit.UserService
import com.blur.blur.domain.usecase.main.userinfo.ChangeNameUseCase
import java.io.IOException
import javax.inject.Inject


class ChangeNameUseCaseImpl @Inject constructor(
    private val userService: UserService,
) : ChangeNameUseCase {
    override suspend fun invoke(type: String, name: String): Result<String> {
        return try {
            // 서버에 이메일 변경 요청을 보냅니다.
            userService.changeName(type = type, name = name)
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

