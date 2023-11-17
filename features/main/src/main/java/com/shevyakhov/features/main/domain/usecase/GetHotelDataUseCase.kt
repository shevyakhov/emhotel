package com.shevyakhov.features.main.domain.usecase

import com.shevyakhov.features.main.domain.entity.HotelData
import com.shevyakhov.features.main.domain.repository.HotelRepository

class GetHotelDataUseCase(private val repository: HotelRepository) {

	suspend operator fun invoke(): HotelData =
		repository.getHotelData()

}