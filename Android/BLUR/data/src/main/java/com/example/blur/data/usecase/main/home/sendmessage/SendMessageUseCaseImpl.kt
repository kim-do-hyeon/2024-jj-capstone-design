package com.example.blur.data.usecase.main.home.sendmessage

import android.content.Context
import com.example.blur.data.di.SharedPreferencesManager
import com.example.blur.data.model.main.home.sendmessage.SendMessageRequest
import com.example.blur.data.model.main.home.sendmessage.SendMessageResponse
import com.example.blur.data.retrofit.UserService
import com.example.blur.domain.usecase.main.home.send.SendMessageUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SendMessageUseCaseImpl @Inject constructor(
    private val context: Context,
    private val userService: UserService
) : SendMessageUseCase {

    override suspend fun invoke(
        senderUsername: String,
        receiverUsername: String,
        content: String
    ): Result<String> {

        val request = SendMessageRequest(senderUsername, receiverUsername, content)
        // senderUsername와 receiverUsername를 사용하여 SendMessageRequest를 생성합니다.

        val call = userService.sendMessage(request)

        return try {
            val response = call.execute()
            if (response.isSuccessful) {
                val sendMessageResponse = response.body()
                val message = sendMessageResponse?.message ?: ""
                Result.success(message)
            } else {
                Result.failure(Exception("Failed to send message: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
