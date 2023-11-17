package com.shevyakhov.feature.main.presentation

import com.shevyakhov.feature.main.domain.entity.HotelData

sealed class UiState {
	object Initial : UiState()
	class Error(val e: Throwable) : UiState()
	data class Content(
		val hotelData: HotelData,
	) : UiState()
}
