package com.example.blur.domain.model

data class User(
    val id:Long,
    val email:String,
    val username:String,
    val profileImageUrl:String? = null,
)