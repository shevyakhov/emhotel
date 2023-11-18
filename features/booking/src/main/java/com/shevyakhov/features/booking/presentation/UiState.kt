package com.shevyakhov.features.booking.presentation

import com.shevyakhov.features.booking.domain.entity.BookingData

sealed class UiState {
	object Initial : UiState()
	class Error(val e: Throwable) : UiState()
	data class Content(
		val hotelData: BookingData,
	) : UiState()
}
