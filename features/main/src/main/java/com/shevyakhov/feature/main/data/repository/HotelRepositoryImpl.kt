package com.shevyakhov.feature.main.data.repository

import com.shevyakhov.feature.main.data.datasource.HotelDataSource
import com.shevyakhov.feature.main.domain.entity.HotelData
import com.shevyakhov.feature.main.domain.repository.HotelRepository

class HotelRepositoryImpl(private val hotelDataSource: HotelDataSource) : HotelRepository {

	override suspend fun getHotelData(): HotelData =
		hotelDataSource.getHotelData()
}