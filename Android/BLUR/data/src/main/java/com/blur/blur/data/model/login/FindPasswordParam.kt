package com.blur.blur.data.model.login

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@Serializable
data class FindPasswordParam(
    val username:String,
    val email:String
){

    fun toRequestBody(): RequestBody {
        return Json.encodeToString(this).toRequestBody()
    }
}