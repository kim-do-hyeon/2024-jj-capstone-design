package com.blur.blur.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TodoItem(
    val id: Int,
    val date: String,
    val text: String,
    val status: Int
)