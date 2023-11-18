package com.shevyakhov.features.booking.domain.usecase

import com.shevyakhov.features.booking.domain.entity.BookingData
import com.shevyakhov.features.booking.domain.repository.BookingRepository

class GetBookingDataUseCase(private val repository: BookingRepository) {

	suspend operator fun invoke(): BookingData =
		repository.getBookingData()

}