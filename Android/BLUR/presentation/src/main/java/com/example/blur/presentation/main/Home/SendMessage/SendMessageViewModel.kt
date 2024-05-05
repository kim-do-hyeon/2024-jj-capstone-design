package com.example.blur.presentation.Main.Home.SendMessage

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blur.data.di.SharedPreferencesManager
import com.example.blur.data.model.main.home.sendmessage.GetMessage
import com.example.blur.data.retrofit.UserService
import com.example.blur.domain.usecase.main.home.SendMessage.GetMessageUseCase
import com.example.blur.domain.usecase.main.home.SendMessage.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
class SendMessageViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userService: UserService,
    private val SendMessageUseCase: SendMessageUseCase,
    private val getMessageUseCase: GetMessageUseCase,
) : ViewModel(), ContainerHost<SendMessageState, SendMessageSideEffect> {

    val sharedPreferencesManager = SharedPreferencesManager

    override val container: Container<SendMessageState, SendMessageSideEffect> = container(
        initialState = SendMessageState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(SendMessageSideEffect.Toast(throwable.message.orEmpty()))
                }
            }
        }
    )

    private val _messageInfo = MutableStateFlow<List<GetMessage>?>(null) // 메시지 정보를 저장하는 StateFlow
    val messageInfo = _messageInfo.asStateFlow()

    init{
        getmessage()
    }

    fun onSendMessageChange(SendMessage: String) = blockingIntent {
        reduce { state.copy(SendMessage = SendMessage) } // 상태 업데이트
    }


    private fun getmessage() {
        viewModelScope.launch {
            try {
                val getUsernameFromPrefs = sharedPreferencesManager.getUsername(context) ?: ""
                val response = userService.getMessages(getUsernameFromPrefs)
                if (response.isSuccessful) {
                    val messageResponse = response.body()
                    if (messageResponse != null) {
                        _messageInfo.value = messageResponse.messages
                        val messages = messageInfo.value
                        val contentList = mutableListOf<String>()
                        val timestampList = mutableListOf<String>()

                        messages?.forEach { message ->
                            contentList.add(message.content)
                            timestampList.add(message.timestamp)
                        }

                    } else {
                        println("Response body is null")
                    }
                } else {
                    println("Failed to fetch user info: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Exception occurred: ${e.message}")
            }
        }
    }

    fun onSendMessageClick() = intent {
        val sendMessage = state.SendMessage ?: return@intent // 메시지가 null이면 처리하지 않음

        val senderUsernameFromPrefs = sharedPreferencesManager.getUsername(context) ?: ""
        // SharedPreferencesManager를 사용하여 저장된 senderUsername를 가져옵니다.

        val receiverUsernameFromPrefs = sharedPreferencesManager.getUsername(context) ?: ""
        // SharedPreferencesManager를 사용하여 저장된 receiverUsername를 가져옵니다.

        // 로그 추가
        Log.e("SendMessageViewModel", "Sending message: $sendMessage")

        postSideEffect(SendMessageSideEffect.ShowLoading) // 로딩 표시

        val result = SendMessageUseCase(senderUsernameFromPrefs, receiverUsernameFromPrefs, sendMessage).fold(
            onSuccess = {
                postSideEffect(SendMessageSideEffect.HideLoading) // 로딩 숨김
                postSideEffect(SendMessageSideEffect.Toast("Message 전송 성공")) // 성공 토스트 메시지
                getmessage()
                reduce { state.copy(SendMessage = "") }
            },
            onFailure = {
                postSideEffect(SendMessageSideEffect.HideLoading) // 로딩 숨김
                postSideEffect(SendMessageSideEffect.Toast("Message 전송 실패")) // 실패 토스트 메시지
            }
        )
    }



}

@Immutable
data class SendMessageState(
    val SendMessage: String? = "",
    val receivedMessage: String? = "", // 받은 메시지를 저장하는 필드 추가
    val contentList: List<String> = emptyList(), // 받은 메시지의 내용을 저장하는 리스트
    val timestampList: List<String> = emptyList() // 받은 메시지의 타임스탬프를 저장하는 리스트
)



sealed interface SendMessageSideEffect {
    class Toast(val message: String) : SendMessageSideEffect // 토스트 메시지 출력
    object ShowLoading : SendMessageSideEffect
    object HideLoading : SendMessageSideEffect // 메인화면으로 이동
}