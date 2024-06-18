package com.blur.blur.data.model.main.setting

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordResponse(
    val result: String,
    val type: String
)
