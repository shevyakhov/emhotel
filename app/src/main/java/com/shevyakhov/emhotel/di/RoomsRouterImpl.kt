package com.shevyakhov.emhotel.di

import com.shevyakhov.features.booking.BookingDestination
import com.shevyakhov.features.hotelrooms.presentation.navigation.RoomsRouter
import com.shevyakhov.libraries.navigation.MainRouter

class RoomsRouterImpl(private val router: MainRouter) : RoomsRouter {

	/**
	* id is fake and should be used on real data
	* */
	override fun navigateToRoomDetails(id: Int) {
		router.open(BookingDestination)
	}

	override fun navigateBack() {
		router.exit()
	}
}