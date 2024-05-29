package com.blur.blur.domain.usecase.main.home.SendMessage



interface GetModelUserUseCase {
    suspend operator fun invoke(
        code:String,
    ): Result<String>
}