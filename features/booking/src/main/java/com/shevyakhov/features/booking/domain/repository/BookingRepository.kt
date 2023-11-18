package com.shevyakhov.features.booking.domain.repository

import com.shevyakhov.features.booking.domain.entity.BookingData

interface BookingRepository {

	suspend fun getBookingData(): BookingData
}