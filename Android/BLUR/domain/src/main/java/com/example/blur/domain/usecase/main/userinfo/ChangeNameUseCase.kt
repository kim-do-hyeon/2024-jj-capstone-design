package com.example.blur.domain.usecase.main.userinfo

interface ChangeNameUseCase {

    suspend operator fun invoke(
        type:String,
        name:String
    ):Result<String>
}