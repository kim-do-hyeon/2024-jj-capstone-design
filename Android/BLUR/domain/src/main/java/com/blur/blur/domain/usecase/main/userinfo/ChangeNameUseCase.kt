package com.blur.blur.domain.usecase.main.userinfo

interface ChangeNameUseCase {

    suspend operator fun invoke(
        type:String,
        name:String
    ):Result<String>
}