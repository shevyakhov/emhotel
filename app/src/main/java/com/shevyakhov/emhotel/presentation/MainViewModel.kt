package com.shevyakhov.emhotel.presentation

import androidx.lifecycle.ViewModel
import com.shevyakhov.features.splash.SplashDestination
import com.shevyakhov.libraries.navigation.MainRouter

class MainViewModel(
	private val router: MainRouter,
) : ViewModel() {

	fun openMainRoot() {
		router.newRoot(SplashDestination())
	}
}