package com.example.blur.data.di

import com.example.blur.data.usecase.main.home.Camera.GetImageListUseCaseImpl
import com.example.blur.domain.usecase.main.home.Camera.GetImageListUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class CameraModule {

    @Binds
    abstract fun bindGetImageListUseCaseImpl(uc: GetImageListUseCaseImpl): GetImageListUseCase
}