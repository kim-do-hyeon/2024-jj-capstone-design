package com.blur.blur.domain.usecase.main.setting

interface ChangePasswordUseCase {
    suspend operator fun invoke(
        current_password: String,
        new_password: String
    ): Result<String>
}