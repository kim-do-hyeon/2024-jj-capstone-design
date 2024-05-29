package com.blur.blur.presentation.Main.Setting.ChangeName

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.blur.blur.presentation.theme.BLURTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            BLURTheme {
                ChangeNameScreen()
            }
        }
    }
}