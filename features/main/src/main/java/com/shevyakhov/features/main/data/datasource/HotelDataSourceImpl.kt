package com.shevyakhov.features.main.data.datasource

import com.shevyakhov.features.main.data.api.HotelApi
import com.shevyakhov.features.main.domain.entity.HotelData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HotelDataSourceImpl(private val api: HotelApi) : HotelDataSource {

	override suspend fun getHotelData(): HotelData =
		withContext(Dispatchers.IO) {
			api.getHotelData()
		}
}