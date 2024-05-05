package com.example.blur.data.model.main.home.widgets


import kotlinx.serialization.Serializable

@Serializable
data class WidgetResponse (
    val result: String,
    val type: String,
    val message: Map<String, List<Int>>? // messages 필드를 nullable로 변경
)
