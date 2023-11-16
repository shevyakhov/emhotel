package com.shevyakhov.emhotel.di

import com.shevyakhov.features.splash.presentation.navigation.SplashRouter
import com.github.terrakok.cicerone.Router
import org.koin.dsl.module

val RouterModule = module {
	factory<SplashRouter> { SplashRouterImpl(get()) }
}