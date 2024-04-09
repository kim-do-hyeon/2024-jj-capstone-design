package com.example.blur.domain.usecase.login

/**
 * @author soohwan.ok
 */
interface SignUpUseCase {

    suspend operator fun invoke(
        email:String,
        username: String,
        password: String
    ):Result<Boolean>
}