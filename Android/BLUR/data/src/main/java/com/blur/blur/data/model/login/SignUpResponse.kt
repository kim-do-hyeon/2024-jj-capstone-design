package com.blur.blur.data.model.login

import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse<T>(
    val result: String,
    val type: String
)