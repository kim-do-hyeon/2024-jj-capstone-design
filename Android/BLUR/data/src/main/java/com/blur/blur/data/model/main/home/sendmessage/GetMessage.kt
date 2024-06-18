package com.blur.blur.data.model.main.home.sendmessage

import kotlinx.serialization.Serializable


@Serializable
data class GetMessage(
    val sender: String,
    val content: String,
    val timestamp: String,
)