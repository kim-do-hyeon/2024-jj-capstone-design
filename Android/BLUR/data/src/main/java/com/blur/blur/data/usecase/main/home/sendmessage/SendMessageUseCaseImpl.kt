package com.blur.blur.data.usecase.main.home.sendmessage

import android.content.Context
import com.blur.blur.data.model.main.home.sendmessage.SendMessageRequest
import com.blur.blur.data.model.main.home.sendmessage.SendMessageResponse
import com.blur.blur.data.retrofit.UserService
import com.blur.blur.domain.usecase.main.home.SendMessage.SendMessageUseCase
import retrofit2.Response
import java.io.IOException
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
        return try {
            val request = SendMessageRequest(senderUsername, receiverUsername, content)
            val response: Response<SendMessageResponse> = userService.sendMessage(request)

            if (response.isSuccessful) {
                val sendMessageResponse = response.body()
                val message = sendMessageResponse?.message ?: ""
                Result.success(message)
            } else {
                Result.failure(Exception("Failed to send message: ${response.message()}"))
            }
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

