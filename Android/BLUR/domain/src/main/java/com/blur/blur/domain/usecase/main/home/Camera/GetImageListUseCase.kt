package com.blur.blur.domain.usecase.main.home.Camera

import com.blur.blur.domain.model.Image

interface GetImageListUseCase {

    suspend operator fun invoke():List<Image>
}