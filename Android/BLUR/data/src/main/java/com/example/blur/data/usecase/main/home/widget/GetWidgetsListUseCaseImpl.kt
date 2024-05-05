package com.example.blur.data.usecase.main.home.widget

import com.example.blur.data.retrofit.UserService
import com.example.blur.domain.usecase.main.home.Widget.GetWidgetsListUseCase
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class GetWidgetsListUseCaseImpl @Inject constructor(
    private val userService: UserService
) : GetWidgetsListUseCase {
    override suspend fun invoke(
        username: String
    ): Result<String> {
        return try {
            val response = userService.getWidgetList(username)
            if (response.isSuccessful) {
                val widgetResponse = response.body()
                val messages = widgetResponse?.message ?: emptyMap()
                val widgetMessages = messages.values.flatten().joinToString(", ")
                if (widgetResponse != null) {
                    Result.success(widgetResponse.toString()) // WidgetResponse를 String으로 변환하여 반환
                } else {
                    Result.failure(Exception("WidgetResponse body is null"))
                }
            } else {
                Result.failure(Exception("Failed to get widget list: ${response.code()}"))
            }
        } catch (e: IOException) {
            // IOException이 발생하면 네트워크 관련 문제로 실패한 것으로 간주하고 실패 결과를 반환합니다.
            Result.failure(e)
        } catch (e: Exception) {
            // 그 외의 예외가 발생하면 일반적인 오류로 처리하고 실패 결과를 반환합니다.
            Result.failure(e)
        }
    }
}
