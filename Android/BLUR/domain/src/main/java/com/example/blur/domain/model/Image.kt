package com.example.blur.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class Image(
    val uri:String,
    val name:String,
    val size:Long,
    val mimeType:String,
)