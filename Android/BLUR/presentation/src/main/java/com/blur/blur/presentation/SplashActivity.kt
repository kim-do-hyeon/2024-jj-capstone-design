package com.blur.blur.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.blur.blur.data.di.SharedPreferencesManager
import com.blur.blur.presentation.Login.LoginActivity
import com.blur.blur.presentation.Main.Calendar.CalendarActivity
import com.blur.blur.presentation.Main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            // SharedPreferencesManager를 사용하여 저장된 쿠키 조회
            val savedCookie = SharedPreferencesManager.getCookie(applicationContext)
            val hasCookie = !savedCookie.isNullOrEmpty()

            if (hasCookie) {
                // 쿠키가 있을 때 (로그인 되어 있을 때)
                startActivity(
                    Intent(this@SplashActivity, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
            } else {
                // 쿠키가 없을 때 (로그인 안되어 있을 때)
                startActivity(
                    Intent(this@SplashActivity, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
            }



        }
    }
}
