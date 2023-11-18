package com.shevyakhov.features.booking.data.datasource

import com.shevyakhov.features.booking.data.api.BookingApi
import com.shevyakhov.features.booking.domain.entity.BookingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookingDataSourceImpl(private val api: BookingApi) : BookingDataSource {

	override suspend fun getBookingData(): BookingData =
		withContext(Dispatchers.IO) {
			api.getBookingData()
		}
}