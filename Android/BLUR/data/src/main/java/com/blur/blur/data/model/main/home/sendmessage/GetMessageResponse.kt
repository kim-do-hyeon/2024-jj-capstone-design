package com.blur.blur.data.model.main.home.sendmessage

import kotlinx.serialization.Serializable

@Serializable
data class GetMessageResponse(
    val result: String,
    val type: String,
    val messages: List<GetMessage>,
)