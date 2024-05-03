package com.example.blur.presentation.Main.Home.Camera

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import com.example.blur.data.di.SharedPreferencesManager
import com.example.blur.data.retrofit.FileService
import com.example.blur.data.usecase.main.Camera.UpLoadFaceImageUseCaseImpl
import com.example.blur.domain.model.Image
import com.example.blur.domain.usecase.main.Camera.GetImageListUseCase
import com.example.blur.domain.usecase.main.Camera.UpLoadFaceImageUseCase
import com.example.blur.presentation.Login.SignUpsideEffect
import com.example.blur.presentation.Main.Home.Camera.CameraX.CameraXActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.io.File
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    @ApplicationContext private val context: Context, // 컨텍스트를 주입받아 앱 전반에서 사용합니다.
    private val getImageListUseCase: GetImageListUseCase,
    private val fileService: FileService, // FileService를 통해 파일 업로드 관련 처리를 합니다.
    private val upLoadFaceImageUseCase: UpLoadFaceImageUseCaseImpl
) : ViewModel(), ContainerHost<GalleryState, GallerySideEffect> {


    private val _image = MutableStateFlow<String?>(null)
    val cookie = _image.asStateFlow()

    override val container: Container<GalleryState, GallerySideEffect> = container(
        initialState = GalleryState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent { postSideEffect(GallerySideEffect.Toast(throwable.message.orEmpty())) }
            }
        }
    )

    init {
        load()
    }

    fun load() = intent {
        val images = getImageListUseCase()
        reduce {
            state.copy(
                selectedImages = images.firstOrNull()?.let { listOf(it) } ?: emptyList(),
                images = images,
                savedSelectedImages = state.savedSelectedImages // 유지된 선택한 이미지 업데이트
            )
        }
    }

    fun upLoadFaceImage() = intent {
        try {
            // `savedSelectedImages`에 대한 파일 생성
            val selectedImagesFile = createSelectedImagesFile(state.savedSelectedImages)
            // 파일 업로드를 위해 UseCase 호출
            val result = upLoadFaceImageUseCase(selectedImagesFile)
            if (result.isSuccess) {
                // 성공적으로 업로드된 경우
                postSideEffect(GallerySideEffect.Toast("이미지 업로드 성공"))
                // 필요에 따라 추가적인 처리 수행
            } else {
                // 업로드 실패한 경우
                postSideEffect(GallerySideEffect.Toast("이미지 업로드 실패"))
                // 실패 이유에 대한 추가적인 처리 수행
            }
        } catch (e: Exception) {
            // 예외 발생 시 처리
            postSideEffect(GallerySideEffect.Toast("이미지 업로드 중 오류 발생: ${e.message}"))
        }
    }

    private fun createSelectedImagesFile(images: List<Image>): File {
        val fileName = "selected_images.txt"
        val file = File(context.cacheDir, fileName)
        val stringBuilder = StringBuilder()
        for (image in images) {
            stringBuilder.append(image.uri).append("\n")
        }
        file.writeText(stringBuilder.toString())
        return file
    }

    fun onAddButton() = intent {
        reduce {
            state.copy(
                savedSelectedImages = state.selectedImages // 현재 선택한 이미지 목록을 저장
            )
        }
        load() // 이미지 목록 로드
        postSideEffect(GallerySideEffect.NavigateUpLoadImageScreen)
    }

    fun onItemClick(image: Image) = intent {
        reduce {
            val updatedSelectedImages = if (state.selectedImages.contains(image)) {
                state.selectedImages - image
            } else {
                state.selectedImages + image
            }
            Log.e("GalleryViewModel", "updatedSelectedImages: $updatedSelectedImages")
            state.copy(
                selectedImages = updatedSelectedImages,
                savedSelectedImages = updatedSelectedImages // 저장된 선택한 이미지 업데이트
            )
        }
    }


}


@Immutable
data class GalleryState(
    val selectedImages: List<Image> = emptyList(),
    val images: List<Image> = emptyList(),
    val savedSelectedImages: List<Image> = emptyList()
)


sealed interface GallerySideEffect {
    class Toast(val message: String) : GallerySideEffect
    object NavigateUpLoadImageScreen : GallerySideEffect
}