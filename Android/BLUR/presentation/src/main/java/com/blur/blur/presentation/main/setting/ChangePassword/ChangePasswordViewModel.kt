package com.blur.blur.presentation.Main.Setting.ChangePassword

import android.content.Context
import androidx.lifecycle.ViewModel
import com.blur.blur.data.di.SharedPreferencesManager
import com.blur.blur.domain.usecase.main.setting.ChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
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

@OptIn(OrbitExperimental::class)
@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val ChangePasswordUseCase: ChangePasswordUseCase,
) : ViewModel(), ContainerHost<ChangePasswordState, ChangePasswordEffect> {

    override val container: Container<ChangePasswordState, ChangePasswordEffect> = container(
        initialState = ChangePasswordState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(ChangePasswordEffect.Toast(throwable.message.orEmpty()))
                }
            }
        }
    )

    fun onPassword1Change(current_password: String) = blockingIntent {
        reduce { state.copy(current_password = current_password) } // 상태 업데이트
    }

    fun onPassword2Change(password2: String) = blockingIntent {
        reduce { state.copy(password2 = password2) } // 상태 업데이트
    }

    fun onPassword3Change(password3: String) = blockingIntent {
        reduce { state.copy(password3 = password3) } // 상태 업데이트
    }

    fun onChangeClick() = intent {
        val currentPassword = state.current_password // 현재 비밀번호 가져오기
        val newPassword = state.password2

        if (currentPassword.isEmpty() || newPassword.isEmpty()) {
            postSideEffect(ChangePasswordEffect.Toast("비밀번호를 모두 입력해주세요."))
            return@intent
        }

        if (currentPassword == newPassword) {
            postSideEffect(ChangePasswordEffect.Toast("현재 비밀번호와 새 비밀번호가 같습니다. 다른 비밀번호를 입력해주세요."))
            return@intent
        }

        if (newPassword != state.password3) {
            postSideEffect(ChangePasswordEffect.Toast("새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다. 다시 확인해주세요."))
            return@intent
        }

        val result = ChangePasswordUseCase(currentPassword, newPassword).getOrThrow()

        SharedPreferencesManager.clearCookie(context)

        // 로그인 활동으로 네비게이션하기 위한 사이드 이펙트를 포스트합니다.
        postSideEffect(ChangePasswordEffect.NavigateToLoginActivity)
    }

}

@Immutable
data class ChangePasswordState(
    val username: String = "",
    val current_password: String = "",
    val password2: String = "",
    val password3: String = "",
)

sealed interface ChangePasswordEffect {
    class Toast(val message: String) : ChangePasswordEffect // 토스트 메시지 출력
    object NavigateToLoginActivity : ChangePasswordEffect // 로그인 화면으로 이동
}

