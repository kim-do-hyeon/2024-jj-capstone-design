package com.example.blur.domain.usecase.main.home.SendMessage



interface GetModelUserUseCase {
    suspend operator fun invoke(
        code:String,
    ): Result<String>
}