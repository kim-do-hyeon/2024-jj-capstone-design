package com.blur.blur.data.model.main.home.widgets


import kotlinx.serialization.Serializable

@Serializable
data class WidgetResponse (
    val message: Map<String, List<Int>>,
    val result: String,
    val type: String,
)
