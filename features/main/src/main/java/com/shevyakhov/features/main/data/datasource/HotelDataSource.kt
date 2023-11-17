package com.shevyakhov.features.main.data.datasource

import com.shevyakhov.features.main.domain.entity.HotelData

interface HotelDataSource {

	suspend fun getHotelData(): HotelData
}