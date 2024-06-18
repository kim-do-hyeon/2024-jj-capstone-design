package com.blur.blur.data.model.login

import kotlinx.serialization.Serializable

@Serializable
data class SessionTestResponse(
    val result: String,
    val type: String,
    val message: String
)
