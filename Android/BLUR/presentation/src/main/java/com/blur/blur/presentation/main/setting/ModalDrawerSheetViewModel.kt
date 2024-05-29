package com.blur.blur.presentation.Main.Setting

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blur.blur.data.di.SharedPreferencesManager
import com.blur.blur.data.model.main.userinfo.UserMessage
import com.blur.blur.data.retrofit.FileService
import com.blur.blur.data.retrofit.UserService
import com.blur.blur.data.usecase.main.userinfo.UpLoadProfileImageUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import java.io.File
import java.io.FileOutputStream
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class ModalDrawerSheetViewModel @Inject constructor(
    @ApplicationContext private val context: Context, // 컨텍스트를 주입받아 앱 전반에서 사용합니다.
    private val userService: UserService, // UserService를 통해 사용자 관련 데이터 처리를 합니다.
    private val fileService: FileService, // FileService를 통해 파일 업로드 관련 처리를 합니다.
    private val uploadProfileImageUseCase: UpLoadProfileImageUseCaseImpl // 이미지 업로드 로직을 처리하는 UseCase를 주입받습니다.
) : ViewModel(), ContainerHost<ModalDrawerSheetState, ModalDrawerSheetSideEffect> {

    private val _cookie = MutableStateFlow<String?>(null) // 사용자의 쿠키 정보를 저장하는 StateFlow입니다.
    val cookie = _cookie.asStateFlow()

    private val _sessionTestResult = MutableStateFlow<String?>(null) // 세션 테스트 결과를 저장하는 StateFlow입니다.
    val sessionTestResult = _sessionTestResult.asStateFlow()

    private val _userInfo = MutableStateFlow<UserMessage?>(null) // 사용자 정보를 저장하는 StateFlow입니다.
    val userInfo = _userInfo.asStateFlow()

    override val container: Container<ModalDrawerSheetState, ModalDrawerSheetSideEffect> =
        container(
            initialState = ModalDrawerSheetState(),
            buildSettings = {
            }
        )

    init {
        loadUserInfo() // 사용자 정보를 불러옴
        loadCookie()   // 쿠키를 불러옴
        checkSession() // 세션을 확인함
    }

    private fun loadCookie() {
        viewModelScope.launch {
            val savedCookie = SharedPreferencesManager.getCookie(context) // SharedPreferences에서 쿠키를 불러옵니다.
            _cookie.value = savedCookie
        }
    }


    private fun checkSession() {
        viewModelScope.launch {
            val response = userService.sessionTest() // 서버에 세션 유효성 테스트를 요청합니다.
            if (response.isSuccessful) {
                _sessionTestResult.value = response.body()?.message // 세션 테스트 성공 시 메시지를 저장합니다.
            } else {
                _sessionTestResult.value = "Session Test Failed: ${response.errorBody()?.string()}" // 실패 시 에러 메시지를 저장합니다.
            }
        }
    }


    private fun loadUserInfo() {
        viewModelScope.launch {
            val response = userService.getUserInfo() // 서버에 사용자 정보를 요청합니다.
            if (response.isSuccessful) {
                _userInfo.value = response.body()?.message // 사용자 정보 요청 성공 시 데이터를 저장합니다.
            } else {
                println("Failed to fetch user info: ${response.errorBody()?.string()}") // 실패 시 콘솔에 에러를 출력합니다.
            }
        }
    }


    // 사용자가 이미지를 선택하고 그 이미지를 서버로 업로드하는 함수입니다.
    fun onImageChange(uri: Uri?) = intent {
        uri?.let { nonNullUri ->
            viewModelScope.launch {
                try {
                    val file = uriToFile(nonNullUri, context) // Uri를 파일로 변환합니다.
                    val result = uploadProfileImageUseCase.invoke(file) // 파일을 서버로 업로드합니다.
                    if (result.isSuccess) {
                        postSideEffect(ModalDrawerSheetSideEffect.ImageUploadSuccess) // 업로드 성공 시 상태 업데이트
                        loadUserInfo() // 사용자 정보를 다시 로드합니다.
                    } else {
                        postSideEffect(ModalDrawerSheetSideEffect.ImageUploadFailure) // 업로드 실패 시 상태 업데이트
                    }
                    file.delete() // 임시 파일을 삭제합니다.
                } catch (e: Exception) {
                    postSideEffect(ModalDrawerSheetSideEffect.ImageUploadFailure) // 예외 발생 시 업로드 실패 처리
                }
            }
        } ?: run {
            postSideEffect(ModalDrawerSheetSideEffect.ImageSelectionCancelled) // 이미지 선택을 취소한 경우
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

    // 로그아웃 처리 함수
    fun onLogoutClick() = intent {
        SharedPreferencesManager.clearCookie(context) // 로컬 저장소에서 쿠키를 삭제합니다.
        postSideEffect(ModalDrawerSheetSideEffect.NavigateToLoginActivity) // 로그인 화면으로 이동 처리를 합니다.
    }

    // 비밀번호 변경 처리 함수
    fun onPasswordChange() = intent {
        postSideEffect(ModalDrawerSheetSideEffect.NavigateToChangePasswordScreen) // 비밀번호 변경 화면으로 이동 처리를 합니다.
    }

    fun onEmailChange() =intent {
        postSideEffect(ModalDrawerSheetSideEffect.NavigateToEmailScreen)
    }

    fun onNameChange() =intent {
        postSideEffect(ModalDrawerSheetSideEffect.NavigateToNameScreen)
    }
}

// ViewModel의 상태를 정의하는 클래스입니다.
@Immutable
data class ModalDrawerSheetState(
    val profileImageUrl: String? = "",
    val originalname: String = "",
)

// ViewModel에서 발생할 수 있는 사이드 이펙트들을 정의하는 인터페이스입니다.
sealed interface ModalDrawerSheetSideEffect {
    object NavigateToLoginActivity : ModalDrawerSheetSideEffect
    object NavigateToChangePasswordScreen : ModalDrawerSheetSideEffect
    object NavigateToEmailScreen : ModalDrawerSheetSideEffect
    object NavigateToNameScreen : ModalDrawerSheetSideEffect
    object ImageSelectionCancelled : ModalDrawerSheetSideEffect
    object ImageUploadFailure : ModalDrawerSheetSideEffect
    object ImageUploadSuccess : ModalDrawerSheetSideEffect
}