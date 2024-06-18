package com.blur.blur.domain.usecase.main.home.todo

import retrofit2.http.Query

interface TodoAddUseCase {
    suspend operator fun invoke(
        username: String,
        localdate: String,
        message: String,
    ): Result<String>
}