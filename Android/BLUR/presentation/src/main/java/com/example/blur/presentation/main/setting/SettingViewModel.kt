package com.example.blur.presentation.main.setting

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blur.data.di.SharedPreferencesManager
import com.example.blur.data.retrofit.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    @ApplicationContext private val context: Context, // Context 주입
    private val userService: UserService, // Retrofit service

) : ViewModel(), ContainerHost<SettingState, SettingSideEffect> {

    private val _cookie = MutableStateFlow<String?>(null)
    val cookie = _cookie.asStateFlow()

    // 세션 테스트 결과를 저장할 MutableStateFlow
    private val _sessionTestResult = MutableStateFlow<String?>(null)
    val sessionTestResult = _sessionTestResult.asStateFlow()

    init {
        load()
        loadCookie()
        checkSession()
    }

    private fun load(){

    }

    private fun loadCookie() {
        viewModelScope.launch {
            val savedCookie = SharedPreferencesManager.getCookie(context)
            _cookie.value = savedCookie
        }
    }

    private fun checkSession() {
        viewModelScope.launch {
            val response = userService.sessionTest()
            if (response.isSuccessful) {
                _sessionTestResult.value = response.body()?.message
            } else {
                _sessionTestResult.value = "Session Test Failed: ${response.errorBody()?.string()}"
            }
        }
    }


    override val container: Container<SettingState, SettingSideEffect> =
        container(
            initialState = SettingState(),
            buildSettings = {
                this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->

                }
            }
        )


    fun onLogoutClick() = intent {
        // SharedPreferencesManager를 사용하여 저장된 쿠키를 지웁니다.
        SharedPreferencesManager.clearCookie(context)

        // 로그인 활동으로 네비게이션하기 위한 사이드 이펙트를 포스트합니다.
        postSideEffect(SettingSideEffect.NavigateToLoginActivity)
    }


    fun onPasswordChange()= intent {

    }

}

@Immutable
data class SettingState(
    val profileImageUrl: String? = null,
    val username: String = "",
)

sealed interface SettingSideEffect {
    object NavigateToLoginActivity: SettingSideEffect
}