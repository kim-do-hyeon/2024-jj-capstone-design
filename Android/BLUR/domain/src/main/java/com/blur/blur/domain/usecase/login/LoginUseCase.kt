package com.blur.blur.domain.usecase.login

/**
 * @author soohwan.ok
 */
interface LoginUseCase {

    suspend operator fun invoke(
        username:String,
        password:String
    ):Result<String>
}