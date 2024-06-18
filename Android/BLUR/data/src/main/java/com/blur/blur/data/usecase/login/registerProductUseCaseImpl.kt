package com.blur.blur.data.usecase.login

import android.content.Context
import com.blur.blur.data.di.SharedPreferencesManager
import com.blur.blur.data.model.login.ProductResponse
import com.blur.blur.data.retrofit.UserService
import com.blur.blur.domain.usecase.login.registerProductUseCase
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class registerProductUseCaseImpl @Inject constructor(
    private val context: Context, // Context 추가
    private val userService: UserService
): registerProductUseCase {
    override suspend fun invoke(code: String): Result<String> {
        return try {
            val response: Response<ProductResponse> = userService.registerProduct(code)
            SharedPreferencesManager.saveProductCode(context, code)
            if (response.isSuccessful) {
                val productResponse: ProductResponse? = response.body()
                if (productResponse != null) {
                    Result.success(productResponse.result)
                } else {
                    Result.failure<String>(NullPointerException("Product response body is null"))
                }
            } else {
                Result.failure<String>(IOException("Failed to register product: ${response.errorBody()}"))
            }
        } catch (e: IOException) {
            Result.failure<String>(e)
        } catch (e: Exception) {
            Result.failure<String>(e)
        }
    }
}