package com.blur.blur.presentation.Main.RegisterProduct

import androidx.lifecycle.ViewModel
import com.blur.blur.domain.usecase.login.registerProductUseCase
import com.blur.blur.presentation.Main.Setting.ChangeName.ChangeNameEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class RegisterProductViewModel @Inject constructor(
    private val registerProductUseCase: registerProductUseCase
):ViewModel(), ContainerHost<RegisterProductState,RegisterProductSideEffect> {
    override val container: Container<RegisterProductState, RegisterProductSideEffect> = container(
        initialState = RegisterProductState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(RegisterProductSideEffect.Toast(throwable.message.orEmpty()))
                }
            }
        }
    )

    fun onDeviceCode(code: String) = blockingIntent {
        val formattedCode = code.formatCode()
        reduce { state.copy(code = formattedCode) } // 형식화된 코드로 상태 업데이트
    }


    fun onClick() = intent {
        val code = state.code

        registerProductUseCase(code).fold(
            onSuccess = {
                // 필요에 따라 추가적인 액션을 수행할 수 있습니다.
                postSideEffect(RegisterProductSideEffect.NavigateToMainActivity)
            },
            onFailure = { throwable ->
                // 변경 실패한 경우
                postSideEffect(RegisterProductSideEffect.Toast(throwable.message ?: "기기등록에 실패 했습니다."))
                // 필요에 따라 추가적인 액션을 수행할 수 있습니다.
            }
        )
    }

    private fun String.formatCode(): String {
        val formattedCode = StringBuilder()
        for (i in 0 until length) {
            if (i % 4 == 0 && i != 0) {
                formattedCode.append("-")
            }
            formattedCode.append(this[i])
        }
        return formattedCode.toString()
    }
    fun onMainScreen() =intent {
        postSideEffect(RegisterProductSideEffect.NavigateToMainActivity)
    }

}

@Immutable
data class RegisterProductState(
    val code: String = ""
)

sealed interface RegisterProductSideEffect{
    class Toast(val message: String) : RegisterProductSideEffect // 토스트 메시지 출력
    object NavigateToMainActivity : RegisterProductSideEffect // 메인화면으로 이동
}