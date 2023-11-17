package com.shevyakhov.emhotel.di

import com.shevyakhov.features.hotelrooms.RoomsDestination
import com.shevyakhov.features.main.presentation.navigation.MainFragmentRouter
import com.shevyakhov.libraries.navigation.MainRouter

class MainFragmentRouterImpl(private val mainRouter: MainRouter) : MainFragmentRouter {

	override fun navigateToHotelRooms() {
		mainRouter.open(RoomsDestination)
	}
}