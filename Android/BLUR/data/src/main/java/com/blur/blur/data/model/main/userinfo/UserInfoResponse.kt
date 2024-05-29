package com.blur.blur.data.model.main.userinfo


import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    val result: String,
    val type: String,
    val message: UserMessage
)
