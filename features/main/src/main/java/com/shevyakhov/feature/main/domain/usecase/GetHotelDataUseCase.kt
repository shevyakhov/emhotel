package com.shevyakhov.feature.main.domain.usecase

import com.shevyakhov.feature.main.domain.entity.HotelData
import com.shevyakhov.feature.main.domain.repository.HotelRepository

class GetHotelDataUseCase(private val repository: HotelRepository) {

	suspend operator fun invoke(): HotelData =
		repository.getHotelData()

}