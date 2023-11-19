package com.shevyakhov.features.orderconfirm.domain

import kotlin.random.Random

class GetOrderDataUseCase {

	private val fromValue = 100000
	private val toValue = 999999

	operator fun invoke(): Int =
		Random.nextInt(fromValue, toValue)
}