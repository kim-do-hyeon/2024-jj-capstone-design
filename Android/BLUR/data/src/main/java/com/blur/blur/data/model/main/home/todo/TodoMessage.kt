package com.blur.blur.data.model.main.home.todo

import kotlinx.serialization.Serializable

@Serializable
data class TodoMessage(
    val id: Int,
    val date: String,
    val text: String,
    val status: Int,
)
