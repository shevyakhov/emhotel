package com.shevyakhov.features.splash.di

import com.shevyakhov.features.splash.presentation.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val SplashModule = module {
	viewModel {
		SplashViewModel(
			router = get()
		)
	}
}