package com.blur.blur.data.model.main.home.widgets

import kotlinx.serialization.Serializable

@Serializable
data class WidgetListResponse(
    val message: Map<String, String>, // Int에서 String으로 변경
    val result: String,
    val type: String,
)
