package com.blur.blur.presentation.Main.Setting.ChangeEmail


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.blur.blur.presentation.theme.BLURTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeEmailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            BLURTheme {
                ChangeEmailScreen()
            }
        }
    }
}