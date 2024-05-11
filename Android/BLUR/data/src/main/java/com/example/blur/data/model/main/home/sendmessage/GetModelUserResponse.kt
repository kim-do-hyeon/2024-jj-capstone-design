package com.example.blur.data.model.main.home.sendmessage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetModelUserResponse(
    val result: String,
    val type: String,
    @SerialName("message") val message: String? // 문자열 형태로 메시지를 받습니다.
)
