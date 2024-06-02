package com.blur.blur.presentation.Main.Home.Camera

sealed class CameraRoute(
    val name:String
) {
    object UpLoadImageScreen : CameraRoute("UpLoadImageScreen")
    object ImageSelectScreen : CameraRoute("ImageSelectScreen")
    object SuccessImage:CameraRoute("SuccessImage")
}