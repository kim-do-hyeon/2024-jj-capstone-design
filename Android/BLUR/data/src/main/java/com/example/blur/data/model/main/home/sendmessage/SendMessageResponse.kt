package com.example.blur.data.model.main.home.sendmessage

import kotlinx.serialization.Serializable

@Serializable
data class SendMessageResponse(
    val result: String,
    val type: String,
    val message: String,
)
