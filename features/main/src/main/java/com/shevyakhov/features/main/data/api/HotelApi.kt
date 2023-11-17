package com.shevyakhov.features.main.data.api

import com.shevyakhov.features.main.domain.entity.HotelData
import retrofit2.http.GET

interface HotelApi {

	@GET("d144777c-a67f-4e35-867a-cacc3b827473")
	suspend fun getHotelData(): HotelData
}