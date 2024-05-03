package com.example.blur.data.retrofit

import com.example.blur.data.model.login.CommonResponse
import com.example.blur.data.model.main.userinfo.UpLoadProfileImageResponse
import com.example.blur.domain.usecase.main.Camera.UpLoadFaceImageUseCase
import okhttp3.MultipartBody
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileService {

    @POST(" /register/profile")
    @Multipart
    @Headers("ContentType:multipart/form-data;")
    suspend fun uploadprofileimage(
        @Part profileImage: MultipartBody.Part
    ): CommonResponse<UpLoadProfileImageResponse>


    @POST(" /register/face")
    @Multipart
    @Headers("ContentType:multipart/form-data;")
    suspend fun uploadfcaeimage(
        @Part profileImage: MultipartBody.Part
    ): CommonResponse<UpLoadFaceImageUseCase>
}