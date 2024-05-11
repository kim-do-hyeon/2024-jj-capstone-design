package com.example.blur.presentation.Main.Home.SendMessage

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blur.data.di.SharedPreferencesManager
import com.example.blur.data.model.main.home.sendmessage.GetMessage
import com.example.blur.data.retrofit.UserService
import com.example.blur.domain.usecase.main.home.SendMessage.SendMessageUseCase
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
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
        onGetUser()
    }

    fun onSendMessageChange(SendMessage: String) = blockingIntent {
        reduce { state.copy(SendMessage = SendMessage) } // 상태 업데이트
    }

    fun onReceiver_username(username: String) = intent {
        reduce { state.copy(onReceiver_username = username) } // ViewModel의 상태 변경
    }


    fun getmessage()=intent {
        viewModelScope.launch {
            try {
//                val getUsernameFromPrefs = sharedPreferencesManager.getUsername(context) ?: ""
                val response = userService.getMessages(state.onReceiver_username)
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

        val onReceiver_username = state.onReceiver_username
        // 로그 추가
        Log.e("SendMessageViewModel", "Sending message: $sendMessage")

        postSideEffect(SendMessageSideEffect.ShowLoading) // 로딩 표시

        val result = SendMessageUseCase(senderUsernameFromPrefs, onReceiver_username, sendMessage).fold(
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

    fun onGetUser() = intent{
        viewModelScope.launch {
            try {
                // 공유 프레퍼런스에서 제품 코드 가져오기
                val getModelCode = sharedPreferencesManager.getProductCode(context) ?: ""
                // 사용자 정보 요청
                val response = userService.getModelUser(getModelCode)

                // 요청이 성공적으로 수행되었는지 확인
                if (response.isSuccessful) {
                    // 서버 응답으로부터 받은 메시지를 JSON 형식의 문자열로 변환합니다.
                    val jsonString = response.body()?.message

                    // Gson 라이브러리를 사용하여 JSON 문자열을 리스트로 변환합니다.
                    val gson = Gson()
                    val listType = object : TypeToken<List<String>>() {}.type
                    val newUsernameList: List<String> = gson.fromJson(jsonString, listType)

                    // 변환된 사용자 목록을 로그에 출력합니다.
                    Log.e("변환", newUsernameList.toString())

                    // 상태를 업데이트합니다.
                    reduce {
                        state.copy(
                            UserList = newUsernameList.toList()
                        )
                    }
                    // 업데이트된 사용자 목록을 로그에 출력합니다.
                    Log.e("리스트", state.UserList.toString())
                }
            } catch (e: Exception) {
                Log.e("MessageUserListViewModel", "Exception occurred: ${e.message}")
            }
        }
    }


}

@Immutable
data class SendMessageState(
    val SendMessage: String? = "",
    val receivedMessage: String? = "", // 받은 메시지를 저장하는 필드 추가
    val contentList: List<String> = emptyList(), // 받은 메시지의 내용을 저장하는 리스트
    val timestampList: List<String> = emptyList(), // 받은 메시지의 타임스탬프를 저장하는 리스트
    val onReceiver_username: String = "", // 수정된 부분: nullable이 아닌 String 타입으로 변경
    val UserList: List<String> = emptyList() // 여러 사용자 이름을 보관하는 리스트
)


sealed interface SendMessageSideEffect {
    class Toast(val message: String) : SendMessageSideEffect // 토스트 메시지 출력
    object ShowLoading : SendMessageSideEffect
    object HideLoading : SendMessageSideEffect // 메인화면으로 이동
}