package com.example.blur.data.model.login

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val result: String,
    val type: String
)