package com.shevyakhov.features.booking.domain.entity

import com.shevyakhov.features.booking.presentation.adapter.Tourist

data class PaymentInfo(
	private val tourist: List<Tourist>,
	private val email: String,
	private val phone: String,
)