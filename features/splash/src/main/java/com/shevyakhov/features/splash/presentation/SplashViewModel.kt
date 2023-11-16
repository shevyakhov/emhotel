package com.shevyakhov.features.splash.presentation

import androidx.lifecycle.ViewModel
import com.shevyakhov.features.splash.presentation.navigation.SplashRouter

class SplashViewModel(private val router: SplashRouter) : ViewModel() {
	init {
		router.goToHome()
	}
}