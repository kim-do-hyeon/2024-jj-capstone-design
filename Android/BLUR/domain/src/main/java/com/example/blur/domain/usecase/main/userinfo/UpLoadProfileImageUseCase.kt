package com.example.blur.domain.usecase.main.userinfo

import java.io.File

interface UpLoadProfileImageUseCase {
    suspend operator fun invoke(
        profile_image: File
    ):Result<String>
}