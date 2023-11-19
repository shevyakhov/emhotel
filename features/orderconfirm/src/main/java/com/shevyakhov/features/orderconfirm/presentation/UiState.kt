package com.shevyakhov.features.orderconfirm.presentation

sealed class UiState {
	object Initial : UiState()
	class Error(val e: Throwable) : UiState()
	data class Content(
		val orderNumber: Int,
	) : UiState()
}