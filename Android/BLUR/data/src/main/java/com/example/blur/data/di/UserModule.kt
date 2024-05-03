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
import com.example.blur.data.usecase.main.Camera.UpLoadFaceImageUseCaseImpl
import com.example.blur.data.usecase.main.home.sendmessage.GetMessageUseCaseImpl
import com.example.blur.data.usecase.main.home.sendmessage.SendMessageUseCaseImpl
import com.example.blur.data.usecase.main.userinfo.ChangeEmailUseCaseImpl
import com.example.blur.data.usecase.main.userinfo.ChangeNameUseCaseImpl
import com.example.blur.data.usecase.main.userinfo.UpLoadProfileImageUseCaseImpl
import com.example.blur.domain.usecase.login.FindPasswordUseCase
import com.example.blur.domain.usecase.login.LoginUseCase
import com.example.blur.domain.usecase.login.SignUpUseCase
import com.example.blur.domain.usecase.main.Camera.UpLoadFaceImageUseCase
import com.example.blur.domain.usecase.main.home.send.GetMessageUseCase
import com.example.blur.domain.usecase.main.home.send.SendMessageUseCase
import com.example.blur.domain.usecase.main.setting.ChangePasswordUseCase
import com.example.blur.domain.usecase.main.userinfo.ChangeEmailUseCase
import com.example.blur.domain.usecase.main.userinfo.ChangeNameUseCase
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

    @Binds
    abstract fun bindChangeEmailUseCase(uc: ChangeEmailUseCaseImpl): ChangeEmailUseCase

    @Binds
    abstract fun bindChangeNameUseCase(uc: ChangeNameUseCaseImpl):ChangeNameUseCase

    @Binds
    abstract fun bindSendMessageUseCase(uc: SendMessageUseCaseImpl): SendMessageUseCase

    @Binds
    abstract fun bindGetMessageUseCase(uc:GetMessageUseCaseImpl): GetMessageUseCase

    @Binds
    abstract fun bindUpLoadFaceImageUseCase(uc: UpLoadFaceImageUseCaseImpl): UpLoadFaceImageUseCase

}
