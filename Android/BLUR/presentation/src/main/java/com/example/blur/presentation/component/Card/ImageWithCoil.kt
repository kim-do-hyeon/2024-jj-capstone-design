package com.example.blur.presentation.Component.Card


import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import coil.compose.rememberImagePainter

@Composable
fun ImageWithCoil( uri: Uri?, modifier: Modifier = Modifier) {
    uri?.let {
        Image(
            painter = rememberImagePainter(data = it),
            contentDescription = null, // 이미지에 대한 설명이 필요하지 않은 경우 null 전달
            modifier = modifier
        )
    }
}

