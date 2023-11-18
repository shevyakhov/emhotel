package com.shevyakhov.features.booking.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevyakhov.features.booking.domain.usecase.GetBookingDataUseCase
import com.shevyakhov.features.booking.presentation.navigation.BookingRouter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookingViewModel(
	private val router: BookingRouter,
	private val getBookingDataUseCase: GetBookingDataUseCase,
) : ViewModel() {

	private val handler = CoroutineExceptionHandler { _, throwable ->
		_uiState.value = UiState.Error(throwable)
	}

	private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
	val uiState: StateFlow<UiState>
		get() = _uiState

	init {
		loadData()
	}

	private fun loadData() = viewModelScope.launch(handler) {
		val bookingData = getBookingDataUseCase()
		_uiState.value = UiState.Content(bookingData)
	}

	fun retryLoading() = viewModelScope.launch(handler) {
		delay(5000L)
		loadData()
	}

	fun navigateToConfirmOrder() {
		router.navigateToConfirmOrder()
	}

	fun navigateBack() {
		router.navigateBack()
	}
}