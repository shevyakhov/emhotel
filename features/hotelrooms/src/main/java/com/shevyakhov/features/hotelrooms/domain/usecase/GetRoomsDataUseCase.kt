package com.shevyakhov.features.hotelrooms.domain.usecase

import com.shevyakhov.features.hotelrooms.domain.entity.RoomsData
import com.shevyakhov.features.hotelrooms.domain.repository.RoomsRepository

class GetRoomsDataUseCase(private val repository: RoomsRepository) {

	suspend operator fun invoke(): RoomsData =
		repository.getRoomsData()

}