package com.example.blur.data.model

import kotlinx.serialization.Serializable

/**
 * @author soohwan.ok
 */
@Serializable
data class CommonResponse<T>(
    val result: String,
    val message: String?,
    val type: String
)