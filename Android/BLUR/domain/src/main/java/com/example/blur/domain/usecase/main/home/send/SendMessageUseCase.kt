package com.example.blur.domain.usecase.main.home.send

interface SendMessageUseCase {
    suspend operator fun invoke(
        senderUsername: String,
        receiverUsername: String,
        content: String
    ): Result<String>
}