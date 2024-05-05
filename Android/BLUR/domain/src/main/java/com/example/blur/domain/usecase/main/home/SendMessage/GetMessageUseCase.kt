package com.example.blur.domain.usecase.main.home.SendMessage


interface GetMessageUseCase {
    suspend operator fun invoke(
        username:String,
    ): Result<String>
}