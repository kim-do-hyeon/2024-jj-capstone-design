import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
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
        val message: String?
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
    ): Call<Void>

}
