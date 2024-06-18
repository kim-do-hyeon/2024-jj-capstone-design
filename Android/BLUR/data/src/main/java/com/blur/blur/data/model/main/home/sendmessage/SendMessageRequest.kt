package com.blur.blur.data.model.main.home.sendmessage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendMessageRequest(
    @SerialName("sender_username") val senderUsername: String,
    @SerialName("receiver_username") val receiverUsername: String,
    @SerialName("content") val content: String
)