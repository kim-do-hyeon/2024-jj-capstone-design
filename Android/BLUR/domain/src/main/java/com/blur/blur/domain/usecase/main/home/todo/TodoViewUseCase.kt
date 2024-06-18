package com.blur.blur.domain.usecase.main.home.todo

interface TodoViewUseCase {
    suspend operator fun invoke(
        username: String,
        localdate: String,
    ): Result<String>
}