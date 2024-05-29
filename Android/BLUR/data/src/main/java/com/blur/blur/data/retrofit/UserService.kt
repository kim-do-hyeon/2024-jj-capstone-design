package com.blur.blur.data.retrofit

import com.blur.blur.data.model.login.CommonResponse
import com.blur.blur.data.model.login.ProductResponse
import com.blur.blur.data.model.login.SessionTestResponse
import com.blur.blur.data.model.login.SignUpResponse
import com.blur.blur.data.model.main.home.sendmessage.GetMessageResponse
import com.blur.blur.data.model.main.home.sendmessage.GetModelUserResponse
import com.blur.blur.data.model.main.home.sendmessage.SendMessageRequest
import com.blur.blur.data.model.main.home.sendmessage.SendMessageResponse
import com.blur.blur.data.model.main.home.widgets.SetWidgetResponse
import com.blur.blur.data.model.main.home.widgets.WidgetListResponse
import com.blur.blur.data.model.main.home.widgets.WidgetResponse
import com.blur.blur.data.model.main.setting.ChangePasswordResponse
import com.blur.blur.data.model.main.userinfo.ProfileResponse
import com.blur.blur.data.model.main.userinfo.UserInfoResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Response<CommonResponse<String>> // Retrofit의 Response 타입으로 변경

    @POST("/register/user/")
    suspend fun signup(
        @Query("password") password: String,
        @Query("email") email: String,
        @Query("username") username: String,
        @Query("originalname") originalname: String,
    ): SignUpResponse<String>

    @POST("/session_test")
    suspend fun sessionTest(): Response<SessionTestResponse>

    @GET("/reset_password")
    fun findPassword(
        @Query("username") username: String,
        @Query("email") email: String,
    ): Call<ResponseBody>


    @POST("/change_password")
    suspend fun changePassword(
        @Query("current_password") current_password: String,
        @Query("new_password") new_password: String,
    ): Response<ChangePasswordResponse>

    @POST("/get_user_info")
    suspend fun getUserInfo(): Response<UserInfoResponse>


    @POST("/change_profile")
    suspend fun changeEmail(
        @Query("type") type: String,
        @Query("email") email: String?, // type이 email일 때 사용될 필드
    ): ProfileResponse

    @POST("/change_profile")
    suspend fun changeName(
        @Query("type") type: String,
        @Query("name") name: String?, // type이 email일 때 사용될 필드
    ): ProfileResponse

    @GET("/get_messages/{username}")
    suspend fun getMessages(
        @Path("username") username: String
    ): Response<GetMessageResponse>

    @POST("/send_message")
    suspend fun sendMessage(
        @Body request: SendMessageRequest
    ): Response<SendMessageResponse>

    @POST("/register/product")
    suspend fun registerProduct(
        @Query("code") code:String
    ): Response<ProductResponse>

    @GET("/widgets")
    suspend fun getWidget():Response<WidgetListResponse>


    @POST("/widgets_custom")
    suspend fun setWidget(
        @Query("model_code") model_code:String,
        @Query("index")  index: String
    ): Response<SetWidgetResponse>

    @GET("/get_widgets_custom/{username}")
    suspend fun getWidgetList(
        @Path("username") username: String
    ): Response<WidgetResponse>

    @GET("/get_model_user_list")
    suspend fun getModelUser(
        @Query("code") code:String,
    ):Response<GetModelUserResponse>

}

