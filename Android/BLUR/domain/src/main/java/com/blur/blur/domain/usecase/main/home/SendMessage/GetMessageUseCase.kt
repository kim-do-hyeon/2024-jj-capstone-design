package com.blur.blur.domain.usecase.main.home.SendMessage


interface GetMessageUseCase {
    suspend operator fun invoke(
        username:String,
    ): Result<String>
}