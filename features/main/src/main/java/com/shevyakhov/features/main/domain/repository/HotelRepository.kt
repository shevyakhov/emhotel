package com.shevyakhov.features.main.domain.repository

import com.shevyakhov.features.main.domain.entity.HotelData

interface HotelRepository {

	suspend fun getHotelData(): HotelData
}