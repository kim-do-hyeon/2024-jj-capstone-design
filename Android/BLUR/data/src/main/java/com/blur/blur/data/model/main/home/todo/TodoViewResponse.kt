package com.blur.blur.data.model.main.home.todo

import kotlinx.serialization.Serializable

@Serializable
data class TodoViewResponse(
    val message: String,
    val result: String,
    val type: String
)

