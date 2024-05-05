package com.example.blur.domain.usecase.main.home.Widget

interface GetWidgetsListUseCase {
    suspend operator fun invoke(
        username:String,
    ):Result<String>
}

