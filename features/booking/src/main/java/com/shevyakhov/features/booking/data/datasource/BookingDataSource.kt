package com.shevyakhov.features.booking.data.datasource

import com.shevyakhov.features.booking.domain.entity.BookingData

interface BookingDataSource {

	suspend fun getBookingData(): BookingData
}