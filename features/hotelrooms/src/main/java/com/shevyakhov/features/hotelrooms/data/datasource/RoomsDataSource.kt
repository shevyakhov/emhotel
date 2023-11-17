package com.shevyakhov.features.hotelrooms.data.datasource

import com.shevyakhov.features.hotelrooms.domain.entity.RoomsData

interface RoomsDataSource {

	suspend fun getRoomsData(): RoomsData
}