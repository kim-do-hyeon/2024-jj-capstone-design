package com.blur.blur.data.usecase.main.home.widget

import com.blur.blur.data.model.main.home.widgets.SetWidgetResponse
import com.blur.blur.data.retrofit.UserService
import com.blur.blur.domain.usecase.main.home.Widget.SetWidgetUseCase
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import com.google.gson.Gson

class SetWidgetUseCaseImpl @Inject constructor(
    private val userService: UserService
) : SetWidgetUseCase {
    override suspend fun invoke(
        model_code: String,
        index: Map<String, List<Int>>
    ): Result<String> {
        return try {
            // Map을 JSON 문자열로 변환
            val indexJson = Gson().toJson(index)
            // Retrofit을 통해 요청 보내기
            val response: Response<SetWidgetResponse> = userService.setWidget(model_code, indexJson)
            if (response.isSuccessful) {
                val widgetResponse: SetWidgetResponse? = response.body()
                if (widgetResponse != null) {
                    Result.success(widgetResponse.message)
                } else {
                    Result.failure<String>(NullPointerException("Widget response body is null"))
                }
            } else {
                Result.failure<String>(IOException("Failed to set widget: ${response.errorBody()}"))
            }
        } catch (e: IOException) {
            Result.failure<String>(e)
        } catch (e: Exception) {
            Result.failure<String>(e)
        }
    }
}
