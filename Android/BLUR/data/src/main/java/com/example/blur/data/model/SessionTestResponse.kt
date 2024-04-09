package com.example.blur.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SessionTestResponse(val result: String, val type: String, val message: String)
