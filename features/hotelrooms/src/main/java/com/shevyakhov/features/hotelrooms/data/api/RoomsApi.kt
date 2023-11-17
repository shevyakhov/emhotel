package com.shevyakhov.features.hotelrooms.data.api

import com.shevyakhov.features.hotelrooms.domain.entity.RoomsData
import retrofit2.http.GET

interface RoomsApi {

	@GET("8b532701-709e-4194-a41c-1a903af00195")
	suspend fun getRoomsData(): RoomsData
}