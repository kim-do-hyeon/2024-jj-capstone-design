package com.example.blur.data.model.login

import kotlinx.serialization.Serializable

/**
 * @author soohwan.ok
 */
@Serializable
data class CommonResponse<T>(
    val result: String,
    val message: String?=null,
    val type: String
)