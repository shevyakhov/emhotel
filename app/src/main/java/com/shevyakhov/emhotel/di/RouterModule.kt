package com.shevyakhov.emhotel.di

import com.shevyakhov.features.main.presentation.navigation.MainFragmentRouter
import com.shevyakhov.features.splash.presentation.navigation.SplashRouter
import org.koin.dsl.module

val RouterModule = module {
	factory<SplashRouter> { SplashRouterImpl(get()) }
	factory<MainFragmentRouter> { MainFragmentRouterImpl(get()) }
}