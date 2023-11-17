package com.shevyakhov.features.hotelrooms.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevyakhov.features.hotelrooms.domain.usecase.GetRoomsDataUseCase
import com.shevyakhov.features.hotelrooms.presentation.navigation.RoomsRouter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoomsViewModel(
	private val router: RoomsRouter,
	private val getRoomsDataUseCase: GetRoomsDataUseCase,
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
		val hotelData = getRoomsDataUseCase()
		_uiState.value = UiState.Content(hotelData)
	}

	fun navigateToHotelRooms() {
		router.navigateToRoomDetails(0)
	}

	fun retryLoading() = viewModelScope.launch(handler) {
		delay(5000L)
		loadData()
	}
}