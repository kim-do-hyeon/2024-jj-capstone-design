@file:OptIn(OrbitExperimental::class)

package com.example.blur.presentation.Login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import com.example.blur.domain.usecase.login.LoginUseCase
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel(), ContainerHost<LoginState, LoginSideEffect> {


    override val container: Container<LoginState, LoginSideEffect> = container(
        initialState = LoginState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    // 에러 처리 로직
                }
            }
        }
    )

    fun onLoginClick() = intent {
        val username = state.username
        val password = state.password

        val result = loginUseCase(username, password).getOrThrow()

        // 로그인 성공 처리, 메인 액티비티로 이동
        postSideEffect(LoginSideEffect.NavigateToMainActivity)

    }


    fun onIdChange(username: String) = blockingIntent {
        reduce { state.copy(username = username) }
    }

    fun onPasswordChange(password: String) = blockingIntent {
        reduce { state.copy(password = password) }
    }
}

@Immutable
data class LoginState(
    val username: String = "",
    val password: String = "",
)

sealed interface LoginSideEffect {
    object NavigateToMainActivity : LoginSideEffect
}
