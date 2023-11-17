package com.shevyakhov.features.hotelrooms.domain.repository

import com.shevyakhov.features.hotelrooms.domain.entity.RoomsData

interface RoomsRepository {

	suspend fun getRoomsData(): RoomsData
}