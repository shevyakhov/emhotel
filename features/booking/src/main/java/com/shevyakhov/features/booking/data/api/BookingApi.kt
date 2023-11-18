package com.shevyakhov.features.booking.data.api

import com.shevyakhov.features.booking.domain.entity.BookingData
import retrofit2.http.GET

interface BookingApi {

	@GET("63866c74-d593-432c-af8e-f279d1a8d2ff")
	suspend fun getBookingData(): BookingData
}