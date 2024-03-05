import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("/register/user")
    fun registerUser(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("email") email: String
    ): Call<Void>
}
