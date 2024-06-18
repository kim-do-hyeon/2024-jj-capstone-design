package com.blur.blur.presentation.Main.Camera

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.blur.blur.data.usecase.main.home.Camera.UpLoadFaceImageUseCaseImpl
import com.blur.blur.domain.model.Image
import com.blur.blur.domain.usecase.main.home.Camera.GetImageListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.io.File
import java.io.FileOutputStream
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    @ApplicationContext private val context: Context, // 컨텍스트를 주입받아 앱 전반에서 사용합니다.
    private val getImageListUseCase: GetImageListUseCase,

    private val upLoadFaceImageUseCase: UpLoadFaceImageUseCaseImpl
) : ViewModel(), ContainerHost<GalleryState, GallerySideEffect> {

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
        // 각 선택된 이미지를 반복하면서
        for (image in state.savedSelectedImages) {
            try {
                val imageFile = uriToFile(Uri.parse(image.uri), context) // String으로 된 Uri를 Uri 객체로 변환한 후 파일로 변환합니다.
                // 각 이미지 파일에 대해 업로드 함수를 호출합니다.
                val result = upLoadFaceImageUseCase.invoke(imageFile)

                // 업로드가 성공했는지 확인합니다.
                if (result.isSuccess) {
                    // 성공 시 처리, 예를 들어 UI 업데이트 또는 메시지 표시
                    postSideEffect(GallerySideEffect.Toast("이미지 업로드 성공"))
                    postSideEffect(GallerySideEffect.SuccessImage)
                } else if (result.isFailure) {
                    // 실패 시 처리, 예를 들어 에러 메시지 표시
                    result.exceptionOrNull()?.let { exception ->
                        val errorMessage = exception.localizedMessage ?: "이미지 업로드 실패"
                        postSideEffect(GallerySideEffect.Toast(errorMessage))
                    }
                }
            } catch (e: Exception) {
                // 업로드 과정 중 발생한 예외 처리
                postSideEffect(GallerySideEffect.Toast("이미지 업로드 중 오류 발생"))
            }
        }
    }


    // Uri를 File 객체로 변환하는 함수입니다.
    private fun uriToFile(uri: Uri, context: Context): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}") // 임시 파일 생성
        FileOutputStream(file).use { outputStream ->
            inputStream?.copyTo(outputStream)
        }
        inputStream?.close()
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
    object SuccessImage:GallerySideEffect
}