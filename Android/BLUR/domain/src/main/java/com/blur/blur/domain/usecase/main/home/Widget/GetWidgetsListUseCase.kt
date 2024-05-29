package com.blur.blur.domain.usecase.main.home.Widget

interface GetWidgetsListUseCase {
    suspend operator fun invoke(
        username:String,
    ):Result<String>
}

