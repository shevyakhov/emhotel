package com.shevyakhov.features.main.data.repository

import com.shevyakhov.features.main.data.datasource.HotelDataSource
import com.shevyakhov.features.main.domain.entity.HotelData
import com.shevyakhov.features.main.domain.repository.HotelRepository

class HotelRepositoryImpl(private val hotelDataSource: HotelDataSource) : HotelRepository {

	override suspend fun getHotelData(): HotelData =
		hotelDataSource.getHotelData()
}