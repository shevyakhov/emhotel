package com.shevyakhov.features.hotelrooms.data.datasource

import com.shevyakhov.features.hotelrooms.data.api.RoomsApi
import com.shevyakhov.features.hotelrooms.domain.entity.RoomsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomsDataSourceImpl(private val api: RoomsApi) : RoomsDataSource {

	override suspend fun getRoomsData(): RoomsData =
		withContext(Dispatchers.IO) {
			api.getRoomsData()
		}
}