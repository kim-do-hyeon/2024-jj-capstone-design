package com.example.blur.domain.usecase.main.home.Widget

interface SetWidgetUseCase {
    suspend operator fun invoke(
        model_code: String,
        index: Map<String, List<Int>>
    ): Result<String>
}
