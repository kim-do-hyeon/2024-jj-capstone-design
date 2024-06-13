package com.blur.blur.domain.usecase.main.home.todo

interface TodoViewUseCase {
    suspend operator fun invoke(
        localdate: String,
    ): Result<String>
}