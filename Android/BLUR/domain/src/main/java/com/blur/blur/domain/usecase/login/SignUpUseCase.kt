package com.blur.blur.domain.usecase.login

/**
 * @author soohwan.ok
 */
interface SignUpUseCase {

    suspend operator fun invoke(
        originalname:String,
        email:String,
        username: String,
        password: String
    ):Result<Boolean>
}