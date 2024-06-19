package com.blur.blur.presentation.Main.Widgets

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blur.blur.data.di.SharedPreferencesManager
import com.blur.blur.data.retrofit.UserService
import com.blur.blur.domain.usecase.main.home.Widget.GetWidgetsListUseCase
import com.blur.blur.domain.usecase.main.home.Widget.SetWidgetUseCase
import com.google.gson.Gson
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
    private val SetWidgetUseCase: SetWidgetUseCase,
) : ViewModel(), ContainerHost<WidgetsSetteingState, WidgetsSettingSideEffect> {

    val sharedPreferencesManager = SharedPreferencesManager

    override val container: Container<WidgetsSetteingState, WidgetsSettingSideEffect> = container(
        initialState = WidgetsSetteingState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(WidgetsSettingSideEffect.Toast(throwable.message.orEmpty()))
                }
            }
        }
    )


    init {
        loadWidget()
        loadWidgetList()
    }

    fun loadWidgetList() = intent {
        viewModelScope.launch {
            try {
                val response = userService.getWidget()
                if (response.isSuccessful) {
                    val widgetsListResponse = response.body()

                    if (widgetsListResponse != null) {
                        val widgetListResponse = widgetsListResponse.message.values
                        reduce {
                            state.copy(
                                WidgetList = widgetListResponse.toList() // Assuming WidgetList is a List<String>
                            )
                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    fun loadWidget() = intent {
        viewModelScope.launch {
            try {
                val getUsernameFromPrefs = sharedPreferencesManager.getUsername(context) ?: ""
                val response = userService.getWidgetList(getUsernameFromPrefs)
                if (response.isSuccessful) {
                    val widgetResponse = response.body()

                    if (widgetResponse != null) {
                        val widgetList = widgetResponse.message?.keys?.toList() ?: emptyList()
                        reduce {
                            state.copy(
                                messages = widgetResponse.message ?: emptyMap()
                            )
                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
    }


    fun sendMessage(message: Map<String, List<Int>>) {
        intent {
            reduce {
                state.copy(
                    sendMessage = message
                )
            }
            ChangeMessage()
        }
    }

    fun ChangeMessage() = intent {
        val updatedMessages = state.messages?.toMutableMap() ?: mutableMapOf()
        state.sendMessage?.forEach { (key, value) ->
            updatedMessages.entries.removeIf { it.value == value }
            updatedMessages[key] = value
        }
        reduce {
            state.copy(
                messages = updatedMessages.toMap(),
                sendMessage = null
            )
        }
    }

    fun onSetWidget() = intent {
        val modelCode = sharedPreferencesManager.getProductCode(context) ?: ""
        val messageMap = state.messages?.toMutableMap() ?: mutableMapOf()

        // message를 JSON 문자열로 변환
        val messageJsonString = Gson().toJson(messageMap)



        viewModelScope.launch {
            try {
                // 위젯 설정에 필요한 데이터를 사용하여 설정을 시도합니다.
                val result = SetWidgetUseCase(modelCode, messageMap)
                result.onSuccess {
                    // 설정이 성공한 경우에 대한 처리
                }.onFailure { throwable ->
                    // 설정이 실패한 경우에 대한 처리
                }
            } catch (e: Exception) {
            }
        }
    }






}

@Immutable
data class WidgetsSetteingState(
    val messages: Map<String, List<Int>>? = emptyMap(), // emptyMap() 사용
    val WidgetList: List<String> = emptyList(),
    val sendMessage: Map<String, List<Int>>? = null,
    var newMessages: Map<String, List<Int>>? = null,
)

sealed interface WidgetsSettingSideEffect {
    class Toast(val message: String) : WidgetsSettingSideEffect // 토스트 메시지 출력
}
