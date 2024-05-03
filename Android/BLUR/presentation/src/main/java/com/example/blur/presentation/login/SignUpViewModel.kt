@file:OptIn(OrbitExperimental::class) // Orbit 라이브러리의 실험적 기능 사용에 동의

package com.example.blur.presentation.Login

import androidx.lifecycle.ViewModel
import com.example.blur.domain.usecase.login.SignUpUseCase
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

@OptIn(OrbitExperimental::class) // Orbit 실험적 기능 사용에 대한 동의
@HiltViewModel // Hilt를 사용한 ViewModel 의존성 주입
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase, // 회원가입 처리를 위한 UseCase 주입
) : ViewModel(), ContainerHost<SignUpState, SignUpsideEffect> { // Orbit MVI 패턴 적용

    // 초기 상태를 설정하고, 예외 처리 핸들러를 포함하는 컨테이너 생성
    override val container: Container<SignUpState, SignUpsideEffect> = container(
        initialState = SignUpState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                // 예외 발생 시 Toast 사이드 이펙트 발행
                intent { postSideEffect(SignUpsideEffect.Toast(throwable.message.orEmpty())) }
            }
        }
    )

    fun onOriginalNameChange(originalname: String) = blockingIntent {
        reduce {
            state.copy(
                originalname = originalname,
                showUsernameField = originalname.isNotEmpty()
            )
        }
    }

    // 이메일 입력 변경 처리
    fun onEmailChange(email: String) = blockingIntent {
        reduce {
            state.copy(
                email = email,
                // 이메일이 비어있지 않으면 사용자 이름 필드를 표시하도록 설정
                showOriginalnameField = email.isNotEmpty()
            )
        } // 상태 업데이트
    }

    // 사용자 이름 입력 변경 처리
    fun onUsernameChange(username: String) = blockingIntent {
        reduce {
            state.copy(
                username = username,
                showPasswordField = username.isNotEmpty()
            )
        } // 상태 업데이트
    }


    // 비밀번호 입력 변경 처리
    fun onPasswordChange(password: String) = blockingIntent {
        reduce { state.copy(password = password) } // 상태 업데이트
    }

    // 비밀번호 재입력 변경 처리
    fun onRepeatPasswordChange(repeatPassword: String) = blockingIntent {
        reduce { state.copy(repeatPassword = repeatPassword) } // 상태 업데이트
    }


    // 회원가입 버튼 클릭 처리
    fun onSignUpClick() = intent {
        // 비밀번호와 비밀번호 재입력이 일치하지 않는 경우 Toast 사이드 이펙트 발행
        if (state.password != state.repeatPassword) {
            postSideEffect(SignUpsideEffect.Toast("두 패스워드가 일치하지 않습니다."))
            return@intent
        }
        // 회원가입 UseCase 실행 및 결과에 따른 처리
        val isSuccessful = signUpUseCase(
            email = state.email,
            username = state.username,
            password = state.password,
            originalname = state.originalname
        ).getOrThrow()

        // 회원가입 성공 시 로그인 화면으로 이동 및 성공 메시지 출력
        if (isSuccessful) {
            postSideEffect(SignUpsideEffect.NavigateToLoginScreen)
            postSideEffect(SignUpsideEffect.Toast("회원가입에 성공했습니다."))
        }
    }
}

// 회원가입 상태를 담는 데이터 클래스
@Immutable
data class SignUpState(
    val originalname: String = "",
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val showOriginalnameField: Boolean = false,
    val showUsernameField: Boolean = false, // 사용자 이름 필드 표시 여부
    val showPasswordField: Boolean = false,
)

// 사이드 이펙트 정의
sealed interface SignUpsideEffect {
    class Toast(val message: String) : SignUpsideEffect // 토스트 메시지 출력

    object NavigateToLoginScreen : SignUpsideEffect // 로그인 화면으로 이동
}
