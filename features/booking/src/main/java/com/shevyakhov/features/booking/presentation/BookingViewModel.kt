package com.shevyakhov.features.booking.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.shevyakhov.features.booking.domain.entity.PaymentInfo
import com.shevyakhov.features.booking.domain.usecase.GetBookingDataUseCase
import com.shevyakhov.features.booking.presentation.adapter.Tourist
import com.shevyakhov.features.booking.presentation.adapter.TouristAdapter.Companion.BIRTHDAY
import com.shevyakhov.features.booking.presentation.adapter.TouristAdapter.Companion.NAME
import com.shevyakhov.features.booking.presentation.adapter.TouristAdapter.Companion.PASSPORT
import com.shevyakhov.features.booking.presentation.adapter.TouristAdapter.Companion.PASSPORT_DATE
import com.shevyakhov.features.booking.presentation.adapter.TouristAdapter.Companion.RESIDENCY
import com.shevyakhov.features.booking.presentation.adapter.TouristAdapter.Companion.SURNAME
import com.shevyakhov.features.booking.presentation.navigation.BookingRouter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookingViewModel(
	private val router: BookingRouter,
	private val getBookingDataUseCase: GetBookingDataUseCase,
) : ViewModel() {

	val errorChannelFlow = MutableSharedFlow<Unit>()
	private val _touristFlow = MutableStateFlow(listOf(Tourist()))
	val touristFlow: StateFlow<List<Tourist>>
		get() = _touristFlow

	private val _validFlow = MutableStateFlow(
		mutableListOf(
			mapOf(
				NAME to false,
				SURNAME to false,
				BIRTHDAY to false,
				RESIDENCY to false,
				PASSPORT to false,
				PASSPORT_DATE to false
			)
		)
	)

	val emailFlow = MutableStateFlow("")
	val phoneFlow = MutableStateFlow("")

	val validFlow: StateFlow<MutableList<Map<Int, Boolean>>>
		get() = _validFlow

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

	private fun navigateToConfirmOrder(order: String) {
		router.navigateToConfirmOrder(order)
	}

	fun navigateBack() {
		router.navigateBack()
	}

	fun saveData(tourists: MutableList<Tourist>) {
		_touristFlow.value = tourists
	}

	fun addTourist() {
		val state = _uiState.value as? UiState.Content ?: return
		val valid = _validFlow.value
		val map = mapOf(
			NAME to true,
			SURNAME to true,
			BIRTHDAY to true,
			RESIDENCY to true,
			PASSPORT to true,
			PASSPORT_DATE to true
		)
		valid.add(map)
		_validFlow.value = valid

		val list = touristFlow.value.toMutableList()
		list.add(Tourist())
		_touristFlow.value = list
	}

	fun validateFields(tourists: MutableList<Tourist>) {
		viewModelScope.launch {
			val newList = validateTouristsMap(tourists)
			val isEmailValid = emailFlow.value.matches(EMAIL_REGEX.toRegex())
			val isPhoneValid = phoneFlow.value.length == 17

			if (!isEmailValid || !isPhoneValid) {
				errorChannelFlow.emit(Unit)
				return@launch
			}
			newList.forEach { list ->
				list.forEach { (_, b) ->
					if (!b) {
						errorChannelFlow.emit(Unit)
						return@launch
					}
				}
			}
			val email = emailFlow.value
			val phone = phoneFlow.value
			val info = PaymentInfo(tourists, email, phone)
			val res = Gson().toJson(info)
			navigateToConfirmOrder(res)
		}
	}

	private fun validateTouristsMap(tourists: MutableList<Tourist>): MutableList<Map<Int, Boolean>> {
		val newList = validFlow.value
		newList.clear()
		tourists.forEach {
			val map = mapOf(
				NAME to it.name.isNotBlank(),
				SURNAME to it.surname.isNotBlank(),
				BIRTHDAY to it.birthday.matches(DATA_REGEX.toRegex()),
				RESIDENCY to it.residency.isNotBlank(),
				PASSPORT to it.passport.isNotBlank(),
				PASSPORT_DATE to it.passportExpireDate.matches(DATA_REGEX.toRegex()),
			)
			newList.add(map)
		}
		_validFlow.value = newList
		return newList
	}

	companion object {

		const val DATA_REGEX = "\\d{1,2}\\.\\d{1,2}\\.\\d{2,4}"
		const val EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
	}
}