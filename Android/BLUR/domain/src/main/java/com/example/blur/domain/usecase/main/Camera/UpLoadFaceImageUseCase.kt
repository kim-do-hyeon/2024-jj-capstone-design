package com.example.blur.domain.usecase.main.Camera

import java.io.File

interface UpLoadFaceImageUseCase {
    suspend operator fun invoke(
        face_image: File
    ):Result<String>
}