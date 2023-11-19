package com.shevyakhov.emhotel.di

import com.shevyakhov.features.booking.presentation.navigation.BookingRouter
import com.shevyakhov.libraries.navigation.MainRouter

class BookingRouterImpl(private val router: MainRouter) : BookingRouter {

	override fun navigateBack() {
		router.exit()
	}

	override fun navigateToConfirmOrder(order: String) {

	}
}