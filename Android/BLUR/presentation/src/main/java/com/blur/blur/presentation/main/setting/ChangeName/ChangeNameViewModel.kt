package com.blur.blur.presentation.Main.Setting.ChangeName

import android.content.Context
import androidx.lifecycle.ViewModel
import com.blur.blur.domain.usecase.main.userinfo.ChangeNameUseCase

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
class ChangeNameViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val ChangeNameUseCase: ChangeNameUseCase,
) : ViewModel(), ContainerHost<ChangeNameState, ChangeNameEffect> {
    override val container: Container<ChangeNameState, ChangeNameEffect> = container(
        initialState = ChangeNameState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(ChangeNameEffect.Toast(throwable.message.orEmpty()))
                }
            }
        }
    )

    fun onTypeChange(type: String) = blockingIntent {
        reduce { state.copy(type = type) } // 상태 업데이트
    }

    fun onnameChange(name: String) = blockingIntent {
        reduce { state.copy(name = name) } // 상태 업데이트
    }

    fun onChangeClick() = intent {
        val name = state.name
        val type = state.type

        // ChangenameUseCase를 호출하여 이메일 변경 요청을 처리합니다.
        ChangeNameUseCase(type, name).fold(
            onSuccess = {
                // 성공적으로 변경된 경우
                postSideEffect(ChangeNameEffect.Toast("name changed successfully"))
                // 필요에 따라 추가적인 액션을 수행할 수 있습니다.
                postSideEffect(ChangeNameEffect.NavigateToMainActivity)
            },
            onFailure = { throwable ->
                // 변경 실패한 경우
                postSideEffect(ChangeNameEffect.Toast(throwable.message ?: "name change failed"))
                // 필요에 따라 추가적인 액션을 수행할 수 있습니다.
            }
        )
    }
}


@Immutable
data class ChangeNameState(
    val name: String = "",
    val type: String = "name",
)

sealed interface ChangeNameEffect {
    class Toast(val message: String) : ChangeNameEffect // 토스트 메시지 출력
    object NavigateToMainActivity : ChangeNameEffect // 메인화면으로 이동
}
