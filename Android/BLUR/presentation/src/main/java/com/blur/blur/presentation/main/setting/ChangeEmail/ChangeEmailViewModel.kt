package com.blur.blur.presentation.Main.Setting.ChangeEmail

import android.content.Context
import androidx.lifecycle.ViewModel
import com.blur.blur.domain.usecase.main.userinfo.ChangeEmailUseCase
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
class ChangeEmailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val ChangeEmailUseCase: ChangeEmailUseCase,
) : ViewModel(), ContainerHost<ChangeEmailState, ChangeEmailEffect> {
    override val container: Container<ChangeEmailState, ChangeEmailEffect> = container(
        initialState = ChangeEmailState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(ChangeEmailEffect.Toast(throwable.message.orEmpty()))
                }
            }
        }
    )

    fun onTypeChange(type: String) = blockingIntent {
        reduce { state.copy(type = type) } // 상태 업데이트
    }

    fun onEmailChange(email: String) = blockingIntent {
        reduce { state.copy(email = email) } // 상태 업데이트
    }

    fun onChangeClick() = intent {
        val email = state.email
        val type = state.type

        // ChangeEmailUseCase를 호출하여 이메일 변경 요청을 처리합니다.
        ChangeEmailUseCase(type, email).fold(
            onSuccess = {
                // 성공적으로 변경된 경우
                postSideEffect(ChangeEmailEffect.Toast("Email changed successfully"))
                // 필요에 따라 추가적인 액션을 수행할 수 있습니다.
                postSideEffect(ChangeEmailEffect.NavigateToMainActivity)
            },
            onFailure = { throwable ->
                // 변경 실패한 경우
                postSideEffect(ChangeEmailEffect.Toast(throwable.message ?: "Email change failed"))
                // 필요에 따라 추가적인 액션을 수행할 수 있습니다.
            }
        )
    }

    fun onMainScreen() =intent {
        postSideEffect(ChangeEmailEffect.NavigateToMainActivity)
    }
}


@Immutable
data class ChangeEmailState(
    val email: String = "",
    val type: String = "email",
)

sealed interface ChangeEmailEffect {
    class Toast(val message: String) : ChangeEmailEffect // 토스트 메시지 출력
    object NavigateToMainActivity : ChangeEmailEffect // 메인화면으로 이동
}
