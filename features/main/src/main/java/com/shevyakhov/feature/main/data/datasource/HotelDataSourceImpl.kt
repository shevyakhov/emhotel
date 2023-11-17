package com.shevyakhov.feature.main.data.datasource

import com.shevyakhov.feature.main.data.api.HotelApi
import com.shevyakhov.feature.main.domain.entity.HotelData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HotelDataSourceImpl(private val api: HotelApi) : HotelDataSource {

	override suspend fun getHotelData(): HotelData =
		withContext(Dispatchers.IO) {
			api.getHotelData()
		}
}