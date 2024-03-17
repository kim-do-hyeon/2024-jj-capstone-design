package com.example.blur.api

import ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import java.io.File

fun uploadImageToServer(imageFile: File) {
    // Retrofit 클라이언트 생성
    val retrofit = Retrofit.Builder()
        .baseUrl("http://jj.system32.kr/")
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
