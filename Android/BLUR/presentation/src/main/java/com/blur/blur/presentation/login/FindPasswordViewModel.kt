@file:OptIn(OrbitExperimental::class)

package com.blur.blur.presentation.Login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
import com.blur.blur.domain.usecase.login.FindPasswordUseCase as FindPasswordUseCase1


@OptIn(OrbitExperimental::class)
@HiltViewModel
class FindPasswordViewModel @Inject constructor(
    private val findPasswordUseCase: FindPasswordUseCase1,

    ) : ViewModel(), ContainerHost<FindPasswordState, FindPasswordEffect> {
    // 초기 상태를 설정하고, 예외 처리 핸들러를 포함하는 컨테이너 생성
    override val container: Container<FindPasswordState, FindPasswordEffect> = container(
        initialState = FindPasswordState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                // 예외 발생
            }
        }
    )

    // 이메일 입력 변경 처리
    fun onEmailChange(email: String) = blockingIntent {
        reduce {
            state.copy(
                email = email,
                // 이메일이 비어있지 않으면 사용자 이름 필드를 표시하도록 설정
                showUsernameField = email.isNotEmpty()
            )
        } // 상태 업데이트
    }

    // 사용자 이름 입력 변경 처리
    fun onUsernameChange(username: String) = blockingIntent {
        reduce {
            state.copy(
                username = username,
            )
        }
    }

    fun onFindPasswordClick() = intent {
        val username = state.username
        val email = state.email

        // 비밀번호 찾기 UseCase 호출
        val result = findPasswordUseCase(username, email)
        result.fold(
            onSuccess = { tempPassword ->
                // 임시 비밀번호 발급 성공 시, 성공 메시지를 Toast 메시지로 보여주고 로그인 화면으로 이동하는 사이드 이펙트 발생
                postSideEffect(FindPasswordEffect.NavigateToLoginScreen)
            },
            onFailure = { exception ->
            }
        )
    }


}


@Immutable
data class FindPasswordState(
    val email: String = "",
    val username: String = "",
    val showUsernameField: Boolean = false, // 사용자 이름 필드 표시 여부
)

// 사이드 이펙트 정의
sealed interface FindPasswordEffect {
    object NavigateToLoginScreen : FindPasswordEffect // 로그인 화면으로 이동
}
