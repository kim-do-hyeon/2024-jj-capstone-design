package com.blur.blur.domain.usecase.main.userinfo

interface ChangeEmailUseCase
{
    suspend operator fun invoke(
        type:String,
        email:String
    ):Result<String>
}

