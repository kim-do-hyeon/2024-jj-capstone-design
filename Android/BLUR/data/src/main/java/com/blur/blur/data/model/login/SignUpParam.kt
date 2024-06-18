package com.blur.blur.data.model.login

import kotlinx.serialization.Serializable

/**
 * @author soohwan.ok
 */
@Serializable
data class SignUpParam(
    val originalname: String,
    val email: String,
    val username: String,
    val password: String,
) {
}