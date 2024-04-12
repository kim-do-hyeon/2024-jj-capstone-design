package com.example.blur.data.di

import android.content.Context
import com.example.blur.data.usecase.main.setting.ChangePasswordUseCaseImpl
import com.example.blur.data.usecase.FindPasswordUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.example.blur.data.usecase.LoginUseCaseImpl
import com.example.blur.data.usecase.SignUpUseCaseImpl
import com.example.blur.data.usecase.main.userinfo.UpLoadProfileImageUseCaseImpl
import com.example.blur.domain.usecase.login.FindPasswordUseCase
import com.example.blur.domain.usecase.login.LoginUseCase
import com.example.blur.domain.usecase.login.SignUpUseCase
import com.example.blur.domain.usecase.main.setting.ChangePasswordUseCase
import com.example.blur.domain.usecase.main.userinfo.UpLoadProfileImageUseCase

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {

    companion object {
        @Provides
        fun provideContext(@ApplicationContext context: Context): Context {
            return context
        }
    }

    @Binds
    abstract fun bindLoginUseCase(uc: LoginUseCaseImpl): LoginUseCase


    @Binds
    abstract fun bindSignUpUseCase(uc: SignUpUseCaseImpl): SignUpUseCase

    @Binds
    abstract fun bindFindPasswordUseCase(uc: FindPasswordUseCaseImpl): FindPasswordUseCase

    @Binds
    abstract fun bindChangePasswordUseCase(uc: ChangePasswordUseCaseImpl): ChangePasswordUseCase
  
    @Binds
    abstract fun bindUpLoadProfileImageUseCase(uc: UpLoadProfileImageUseCaseImpl): UpLoadProfileImageUseCase


}
