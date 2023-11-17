package com.shevyakhov.feature.main.data.datasource

import com.shevyakhov.feature.main.domain.entity.HotelData

interface HotelDataSource {

	suspend fun getHotelData(): HotelData
}