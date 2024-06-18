package com.blur.blur.data.usecase.main.home.todo

import com.blur.blur.data.retrofit.UserService
import com.blur.blur.domain.usecase.main.home.todo.TodoViewUseCase
import javax.inject.Inject

class TodoViewUseCaseImpl @Inject constructor(
    private val userService: UserService
) : TodoViewUseCase {
    override suspend fun invoke(
        username: String,
        localdate: String
    ): Result<String> {
        return try {
            val response = userService.viewtodo(username,localdate)
            if (response.isSuccessful) {
                val todoResponse = response.body()
                // 여기서 todoResponse를 적절히 처리하고 결과를 반환합니다.
                Result.success("서버 응답에 따른 처리")
            } else {
                Result.failure(Exception("서버 응답이 실패했습니다."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
