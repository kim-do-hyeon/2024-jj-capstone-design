package com.blur.blur.data.model.widget

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WidgetRequest(
    @SerialName("model_code") val modelCode: String,
    @SerialName("index") val index: String
)
