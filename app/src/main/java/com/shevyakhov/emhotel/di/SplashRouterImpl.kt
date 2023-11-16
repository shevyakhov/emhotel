package com.shevyakhov.emhotel.di

import com.shevyakhov.feature.main.HomeDestination
import com.shevyakhov.features.splash.presentation.navigation.SplashRouter
import com.shevyakhov.libraries.navigation.MainRouter

class SplashRouterImpl(private val router: MainRouter) : SplashRouter {

	override fun goToHome() {
		router.open(HomeDestination)
	}
}