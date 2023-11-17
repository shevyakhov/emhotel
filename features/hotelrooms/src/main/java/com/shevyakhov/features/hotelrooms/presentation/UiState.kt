package com.shevyakhov.features.hotelrooms.presentation

import com.shevyakhov.features.hotelrooms.domain.entity.RoomsData

sealed class UiState {
	object Initial : UiState()
	class Error(val e: Throwable) : UiState()
	data class Content(
		val hotelData: RoomsData,
	) : UiState()
}
