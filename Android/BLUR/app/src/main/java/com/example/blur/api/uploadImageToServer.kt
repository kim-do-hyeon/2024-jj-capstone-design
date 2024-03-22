package com.example.blur.api
import ApiService
import com.example.blur.Screen.AccountManagement.Login.SharedPreferencesManager
import com.example.blur.Screen.CameraX.CameraXActivity

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import java.io.File
import java.io.IOException

fun uploadImageToServer(imageFile: File, cameraXActivity: CameraXActivity) {
    // OkHttpClient에 Interceptor를 추가하여 쿠키를 요청에 함께 보내기
    val sessionToken = SharedPreferencesManager.getSessionToken(cameraXActivity.applicationContext)

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                // 기존의 요청
                val originalRequest = chain.request()

                // 쿠키를 요청 헤더에 추가
                val requestWithCookie = originalRequest.newBuilder()
                    .header("Cookie", "session=$sessionToken") // 세션 토큰을 요청 헤더에 추가합니다.
                    .build()

                // 요청 실행
                return chain.proceed(requestWithCookie)
            }
        })
        .build()

    // Retrofit 클라이언트 생성
    val retrofit = Retrofit.Builder()
        .baseUrl("http://jj.system32.kr/")
        .client(okHttpClient)
        .build()

    val service = retrofit.create(ApiService::class.java)

    // 이미지 파일을 RequestBody로 변환
    val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
    val body = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)

    // 서버에 이미지 업로드 요청
    val call = service.uploadImage(body)
    call.enqueue(object : retrofit2.Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
            if (response.isSuccessful) {
                // 이미지 업로드 성공
                println("이미지 업로드 성공")
            } else {
                // 이미지 업로드 실패
                println("이미지 업로드 실패")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            // 통신 실패 처리
            println("통신 실패")
            t.printStackTrace()
        }
    })
}
