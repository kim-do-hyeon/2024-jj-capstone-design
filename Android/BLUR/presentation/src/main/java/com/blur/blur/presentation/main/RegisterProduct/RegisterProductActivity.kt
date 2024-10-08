package com.blur.blur.presentation.Main.RegisterProduct

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.blur.blur.presentation.theme.BLURTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterProductActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            BLURTheme {
                RegisterProductScreen()
            }
        }
    }
}