package com.blur.blur.data.di

import android.content.Context
import com.blur.blur.data.usecase.main.setting.ChangePasswordUseCaseImpl
import com.blur.blur.data.usecase.login.FindPasswordUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.blur.blur.data.usecase.login.LoginUseCaseImpl
import com.blur.blur.data.usecase.login.SignUpUseCaseImpl
import com.blur.blur.data.usecase.login.registerProductUseCaseImpl
import com.blur.blur.data.usecase.main.home.Camera.UpLoadFaceImageUseCaseImpl
import com.blur.blur.data.usecase.main.home.sendmessage.GetMessageUseCaseImpl
import com.blur.blur.data.usecase.main.home.sendmessage.GetModelUserUseCaseImpl
import com.blur.blur.data.usecase.main.home.sendmessage.SendMessageUseCaseImpl
import com.blur.blur.data.usecase.main.home.todo.TodoAddUseCaseImpl
import com.blur.blur.data.usecase.main.home.todo.TodoViewUseCaseImpl
import com.blur.blur.data.usecase.main.home.widget.GetWidgetsListUseCaseImpl
import com.blur.blur.data.usecase.main.home.widget.SetWidgetUseCaseImpl
import com.blur.blur.data.usecase.main.userinfo.ChangeEmailUseCaseImpl
import com.blur.blur.data.usecase.main.userinfo.ChangeNameUseCaseImpl
import com.blur.blur.data.usecase.main.userinfo.UpLoadProfileImageUseCaseImpl
import com.blur.blur.domain.usecase.login.FindPasswordUseCase
import com.blur.blur.domain.usecase.login.LoginUseCase
import com.blur.blur.domain.usecase.login.SignUpUseCase
import com.blur.blur.domain.usecase.login.registerProductUseCase
import com.blur.blur.domain.usecase.main.home.Camera.UpLoadFaceImageUseCase
import com.blur.blur.domain.usecase.main.home.SendMessage.GetMessageUseCase
import com.blur.blur.domain.usecase.main.home.SendMessage.GetModelUserUseCase
import com.blur.blur.domain.usecase.main.home.SendMessage.SendMessageUseCase
import com.blur.blur.domain.usecase.main.home.Widget.GetWidgetsListUseCase
import com.blur.blur.domain.usecase.main.home.Widget.SetWidgetUseCase
import com.blur.blur.domain.usecase.main.home.todo.TodoAddUseCase
import com.blur.blur.domain.usecase.main.home.todo.TodoViewUseCase
import com.blur.blur.domain.usecase.main.setting.ChangePasswordUseCase
import com.blur.blur.domain.usecase.main.userinfo.ChangeEmailUseCase
import com.blur.blur.domain.usecase.main.userinfo.ChangeNameUseCase
import com.blur.blur.domain.usecase.main.userinfo.UpLoadProfileImageUseCase

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
    abstract fun bindregisterProductUseCase(uc: registerProductUseCaseImpl): registerProductUseCase

    @Binds
    abstract fun bindUpLoadFaceImageUseCase(uc: UpLoadFaceImageUseCaseImpl): UpLoadFaceImageUseCase

    @Binds
    abstract fun bindGetWidgetsListUseCase(uc:GetWidgetsListUseCaseImpl): GetWidgetsListUseCase

    @Binds
    abstract fun bindSetWidgetUseCase(uc: SetWidgetUseCaseImpl): SetWidgetUseCase

    @Binds
    abstract fun bindGetModelUserUseCase(uc: GetModelUserUseCaseImpl): GetModelUserUseCase

    @Binds
    abstract fun bindTodoAddUseCase(uc: TodoAddUseCaseImpl): TodoAddUseCase

    @Binds
    abstract fun bindTodoViewUseCase(uc: TodoViewUseCaseImpl): TodoViewUseCase

}
