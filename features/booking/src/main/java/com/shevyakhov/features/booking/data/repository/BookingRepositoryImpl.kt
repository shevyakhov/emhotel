package com.shevyakhov.features.booking.data.repository

import com.shevyakhov.features.booking.data.datasource.BookingDataSource
import com.shevyakhov.features.booking.domain.entity.BookingData
import com.shevyakhov.features.booking.domain.repository.BookingRepository

class BookingRepositoryImpl(private val bookingDataSource: BookingDataSource) : BookingRepository {

	override suspend fun getBookingData(): BookingData =
		bookingDataSource.getBookingData()
}