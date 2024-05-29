package com.blur.blur.domain.usecase.login

interface registerProductUseCase {

    suspend operator fun invoke(
        code:String
    ):Result<String>
}