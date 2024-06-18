package com.blur.blur.data.model.main.home.widgets

import kotlinx.serialization.Serializable

@Serializable
data class SetWidgetResponse(
    val result: String,
    val type:String,
    val message: String,
)