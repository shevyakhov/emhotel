package com.shevyakhov.feature.main.domain.repository

import com.shevyakhov.feature.main.domain.entity.HotelData

interface HotelRepository {

	suspend fun getHotelData(): HotelData
}