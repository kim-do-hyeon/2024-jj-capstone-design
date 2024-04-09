package com.example.blur.presentation.main.Home.CameraX

import android.content.Context
import com.example.blur.data.di.SharedPreferencesManager
import com.example.blur.data.retrofit.UserService
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import java.io.File
import java.io.IOException

fun uploadImageToServer(imageFile: File, cameraXActivity: Context) {
    // 로그를 출력하기 위한 인터셉터 생성
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)

    // OkHttpClient에 Interceptor를 추가하여 쿠키를 요청에 함께 보내기
    val sessionToken = SharedPreferencesManager.getCookie(cameraXActivity.applicationContext)

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging) // 인터셉터를 OkHttpClient에 추가
        .addInterceptor { chain ->
            // 세션 쿠키를 가져와서 헤더에 추가
            val currentSession = SharedPreferencesManager.getCookie(cameraXActivity.applicationContext)
            val request = chain.request().newBuilder()
                .addHeader("Cookie", currentSession ?: "")
                .build()
            chain.proceed(request)
        }
        .build()

    // Retrofit 클라이언트 생성
    val retrofit = Retrofit.Builder()
        .baseUrl("http://jj.system32.kr/")
        .client(okHttpClient)
        .build()

    val service = retrofit.create(UserService::class.java)

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
