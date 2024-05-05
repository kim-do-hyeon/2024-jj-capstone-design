package com.example.blur.domain.usecase.main.home.SendMessage

interface SendMessageUseCase {
    suspend operator fun invoke(
        senderUsername: String,
        receiverUsername: String,
        content: String
    ): Result<String>
}