package com.shevyakhov.emhotel.di

import com.shevyakhov.features.orderconfirm.presentation.navigation.OrderConfirmRouter
import com.shevyakhov.libraries.navigation.MainRouter

class OrderConfirmRouterImpl(private val router: MainRouter) : OrderConfirmRouter {

	override fun navigateBack() {
		router.exit()
	}

	override fun navigateToHomeScreen() {
		router.popToRoot()
	}
}
