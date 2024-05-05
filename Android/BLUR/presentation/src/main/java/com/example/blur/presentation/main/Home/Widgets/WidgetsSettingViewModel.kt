package com.example.blur.presentation.Main.Home.Widgets

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blur.data.di.SharedPreferencesManager
import com.example.blur.data.retrofit.UserService
import com.example.blur.domain.model.WidgetInfo
import com.example.blur.domain.usecase.main.home.Widget.GetWidgetsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class WidgetsSettingViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userService: UserService,
    private val GetWidgetsListUseCase: GetWidgetsListUseCase,
) : ViewModel(), ContainerHost<WidgetsSetteingState, WidgetsStteingSideEffect> {

    val sharedPreferencesManager = SharedPreferencesManager
    private val widgetInfoList = mutableListOf<WidgetInfo>()

    override val container: Container<WidgetsSetteingState, WidgetsStteingSideEffect> = container(
        initialState = WidgetsSetteingState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(WidgetsStteingSideEffect.Toast(throwable.message.orEmpty()))
                }
            }
        }
    )


    init {
        loadWidget()
    }

    fun loadWidget() = intent {
        viewModelScope.launch {
            try {
                val getUsernameFromPrefs = sharedPreferencesManager.getUsername(context) ?: ""
                val response = userService.getWidgetList(getUsernameFromPrefs)
                if (response.isSuccessful) {
                    val widgetResponse = response.body()

                    if (widgetResponse != null) {
                        // WidgetResponse가 null이 아닌 경우 처리할 내용 추가
                        reduce {
                            state.copy(
                                messages = widgetResponse.message ?: emptyMap(),
                            )
                        }
                        Log.e("메시지", state.messages.toString())
                    } else {
                        Log.e("WidgetsSettingViewModel", "WidgetResponse is null")
                    }
                } else {
                    Log.e("WidgetsSettingViewModel", "Response body is null")
                }
            } catch (e: Exception) {
                Log.e("WidgetsSettingViewModel", "Exception occurred: ${e.message}")
            }
        }
    }

}

@Immutable
data class WidgetsSetteingState(
    val messages: Map<String, List<Int>>? = emptyMap(), // emptyMap() 사용

)

sealed interface WidgetsStteingSideEffect {
    class Toast(val message: String) : WidgetsStteingSideEffect // 토스트 메시지 출력
}
