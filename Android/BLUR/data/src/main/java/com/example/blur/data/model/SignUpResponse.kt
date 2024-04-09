package com.example.blur.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse<T>(
    val result: String,
    val type: String
)