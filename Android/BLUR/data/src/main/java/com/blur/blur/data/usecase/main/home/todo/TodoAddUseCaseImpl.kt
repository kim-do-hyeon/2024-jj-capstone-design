package com.blur.blur.data.usecase.main.home.todo

import com.blur.blur.data.retrofit.UserService
import com.blur.blur.domain.usecase.main.home.todo.TodoAddUseCase
import javax.inject.Inject

class TodoAddUseCaseImpl @Inject constructor(
    private val userService: UserService
) : TodoAddUseCase {
    override suspend fun invoke(
        localdate: String,
        message: String
    ): Result<String> {
        return try {
            userService.addtodo(
                localdate = localdate,
                message = message
            )
            Result.success("성공")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
