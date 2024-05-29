package com.blur.blur.data.model.main.userinfo

import kotlinx.serialization.Serializable

@Serializable
data class UserMessage(
    val username: String,
    val email: String,
    val originalname: String,
    val profile_image: String?
)