package com.shevyakhov.feature.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevyakhov.feature.main.domain.usecase.GetHotelDataUseCase
import com.shevyakhov.feature.main.presentation.navigation.MainFragmentRouter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainFragmentViewModel(
	private val router: MainFragmentRouter,
	private val getHotelDataUseCase: GetHotelDataUseCase,
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

	fun loadData() = viewModelScope.launch(handler) {
		val hotelData = getHotelDataUseCase()
		_uiState.value = UiState.Content(hotelData)
	}

	fun navigateToHotelRooms() {
		router.navigateToHotelRooms()
	}

	fun retryLoading() = viewModelScope.launch(handler) {
		delay(5000L)
		loadData()
	}
}