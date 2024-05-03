package com.example.blur.domain.usecase.login

interface FindPasswordUseCase {

    suspend operator fun invoke(
        username:String,
        email:String
    ):Result<String>
}