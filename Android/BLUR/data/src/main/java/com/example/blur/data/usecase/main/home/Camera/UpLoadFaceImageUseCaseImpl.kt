package com.example.blur.data.usecase.main.home.Camera

import com.example.blur.data.retrofit.FileService
import com.example.blur.domain.usecase.main.home.Camera.UpLoadFaceImageUseCase
import com.example.blur.domain.usecase.main.userinfo.UpLoadProfileImageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class UpLoadFaceImageUseCaseImpl @Inject constructor(
    private val fileService: FileService
) : UpLoadFaceImageUseCase {
    override suspend fun invoke(face_image: File): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                // 파일로부터 요청 바디 생성
                val requestFile = face_image.asRequestBody("image/*".toMediaTypeOrNull())
                // "profile_image" 파트 이름으로 MultipartBody.Part 생성
                val body = MultipartBody.Part.createFormData("face_image", face_image.name, requestFile)

                // 이미지 업로드를 위해 서비스 호출
                val response = fileService.uploadfcaeimage(body)

                // 응답 처리
                if (response.result == "success") {
                    // 성공 결과와 메시지 반환
                    Result.success("[이미지 업로드 성공]")
                } else {
                    // 실패 결과 반환
                    Result.failure(Exception("[이미지 업로드 실패]: 얼굴 사진만 업로드가 가능합니다."))
                }
            } catch (e: Exception) {
                // 예외 메시지와 함께 실패 결과 반환
                Result.failure(e)
            }
        }
    }
}
