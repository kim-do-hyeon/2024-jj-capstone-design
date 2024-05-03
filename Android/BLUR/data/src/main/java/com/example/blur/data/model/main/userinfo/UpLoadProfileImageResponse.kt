package com.example.blur.data.model.main.userinfo

import kotlinx.serialization.Serializable

@Serializable
data class UpLoadProfileImageResponse (
    val message: String?,
    val result: String,
    val type: String
)


