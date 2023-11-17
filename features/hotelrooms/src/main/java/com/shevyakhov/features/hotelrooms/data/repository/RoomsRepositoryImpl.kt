package com.shevyakhov.features.hotelrooms.data.repository

import com.shevyakhov.features.hotelrooms.data.datasource.RoomsDataSource
import com.shevyakhov.features.hotelrooms.domain.entity.RoomsData
import com.shevyakhov.features.hotelrooms.domain.repository.RoomsRepository

class RoomsRepositoryImpl(private val roomsDataSource: RoomsDataSource) : RoomsRepository {

	override suspend fun getRoomsData(): RoomsData =
		roomsDataSource.getRoomsData()
}