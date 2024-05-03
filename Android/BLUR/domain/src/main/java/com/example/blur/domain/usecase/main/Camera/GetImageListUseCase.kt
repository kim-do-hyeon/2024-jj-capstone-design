package com.example.blur.domain.usecase.main.Camera

import com.example.blur.domain.model.Image

interface GetImageListUseCase {

    suspend operator fun invoke():List<Image>
}