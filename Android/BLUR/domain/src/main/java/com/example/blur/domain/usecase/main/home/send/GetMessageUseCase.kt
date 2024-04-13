package com.example.blur.domain.usecase.main.home.send


interface GetMessageUseCase {
    suspend operator fun invoke(
        username:String,
    ): Result<String>
}