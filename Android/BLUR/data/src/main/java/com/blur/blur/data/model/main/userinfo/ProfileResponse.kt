package com.blur.blur.data.model.main.userinfo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class ProfileResponse {
    @Serializable
    @SerialName("change_email")
    data class ChangeEmail(
        val result: String,
        val type: String
    ) : ProfileResponse()

    @Serializable
    @SerialName("chage_originalname")
    data class ChangeName(
        val result: String,
        val type: String
    ) : ProfileResponse()
}