package com.example.blur.data.usecase.main.home.sendmessage

import com.example.blur.data.model.main.home.sendmessage.GetMessageResponse
import com.example.blur.data.model.main.home.sendmessage.GetModelUserResponse
import com.example.blur.data.retrofit.UserService
import com.example.blur.domain.usecase.main.home.SendMessage.GetModelUserUseCase
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class GetModelUserUseCaseImpl @Inject constructor(
    private val userService: UserService
) : GetModelUserUseCase {
    override suspend fun invoke(code: String): Result<String> {
        return try {
            val response: Response<GetModelUserResponse> = userService.getModelUser(code)

            if (response.isSuccessful) {
                val userModel = response.body()?.toString() ?: ""
                Result.success(userModel)
            } else {
                Result.failure(Exception("Failed to get model user: ${response.message()}"))
            }
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
