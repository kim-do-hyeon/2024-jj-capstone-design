package com.example.blur.presentation.Main.Home.RegisterProduct

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.blur.presentation.theme.BLURTheme
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