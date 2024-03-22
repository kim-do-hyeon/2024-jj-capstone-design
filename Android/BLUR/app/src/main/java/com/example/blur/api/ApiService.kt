import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @POST("/register/user/")
    fun registerUser(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("email") email: String
    ): Call<Void>

    data class LoginResponse(
        val result: String,
        val message: String?,
        val sessionToken: String
    )

    @GET("/login")
    fun loginUser(
        @Query("username") username: String,
        @Query("password") password: String
    ): Call<LoginResponse>

    @GET("/reset_password")
    fun findPassword(
        @Query("username") username: String,
        @Query("email") email: String
    ): Call<ResponseBody>

    @Multipart
    @POST("development_image")
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<ResponseBody>

    @GET("/widgets")
    fun getWidgets(): Call<WidgetResponse>

    data class WidgetData(
        val widgetId: Int,
        val widgetName: String
    )

    data class WidgetResponse(
        val result: String,
        val type: String,
        val message: Map<Int, String>
    )
}


