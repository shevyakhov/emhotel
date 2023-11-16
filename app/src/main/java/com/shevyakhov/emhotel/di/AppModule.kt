package com.shevyakhov.emhotel.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.shevyakhov.emhotel.di.MainNavigatorName.MAIN
import com.shevyakhov.emhotel.presentation.MainViewModel
import com.shevyakhov.libraries.navigation.MainRouter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object MainNavigatorName {

	const val MAIN = "MAIN"
}

fun buildCicerone(): Cicerone<Router> = Cicerone.create()

val AppModule = module {
	single(named(MAIN)) { buildCicerone() }
	single(named(MAIN)) { get<Cicerone<Router>>(named(MAIN)).router }
	single(named(MAIN)) { get<Cicerone<Router>>(named(MAIN)).getNavigatorHolder() }
	single<MainRouter> { MainRouterImpl(get(named(MAIN))) }

	viewModel { MainViewModel(router = get()) }
}