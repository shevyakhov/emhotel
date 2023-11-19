package com.shevyakhov.features.orderconfirm.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevyakhov.features.orderconfirm.domain.GetOrderDataUseCase
import com.shevyakhov.features.orderconfirm.presentation.navigation.OrderConfirmRouter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderConfirmViewModel(
	private val router: OrderConfirmRouter,
	private val getOrderDataUseCase: GetOrderDataUseCase,
) : ViewModel() {

	private val handler = CoroutineExceptionHandler { _, throwable ->
		_uiState.value = UiState.Error(throwable)
	}

	private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
	val uiState: StateFlow<UiState>
		get() = _uiState

	fun navigateBack() {
		router.navigateBack()
	}

	fun navigateToHomeScreen() {
		router.navigateToHomeScreen()
	}

	fun retryLoading() = viewModelScope.launch(handler) {
		delay(5000L)
		loadData()
	}

	fun loadData() = viewModelScope.launch(handler) {
		val bookingData = getOrderDataUseCase()
		_uiState.value = UiState.Content(bookingData)
	}
}