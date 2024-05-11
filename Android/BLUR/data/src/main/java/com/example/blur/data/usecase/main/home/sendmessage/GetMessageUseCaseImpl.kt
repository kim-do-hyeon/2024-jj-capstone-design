package com.example.blur.data.usecase.main.home.sendmessage

import com.example.blur.data.retrofit.UserService
import com.example.blur.domain.usecase.main.home.SendMessage.GetMessageUseCase
import javax.inject.Inject


class GetMessageUseCaseImpl @Inject constructor(
    private val userService: UserService
) : GetMessageUseCase {

    override suspend fun invoke(
        username: String
    ): Result<String> {
        return try {
            val response = userService.getMessages(username)// Retrofit의 suspend 함수인 await을 사용하여 비동기 호출을 기다립니다.

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.toString()) // body를 문자열로 변환하여 반환
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("Failed to fetch messages: ${response.errorBody()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
