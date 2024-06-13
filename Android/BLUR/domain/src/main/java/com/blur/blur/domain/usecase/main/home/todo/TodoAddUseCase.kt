package com.blur.blur.domain.usecase.main.home.todo

interface TodoAddUseCase {
    suspend operator fun invoke(
        localdate: String,
        message: String,
    ): Result<String>
}