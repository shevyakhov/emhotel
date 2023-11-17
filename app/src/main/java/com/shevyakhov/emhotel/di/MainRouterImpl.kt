package com.shevyakhov.emhotel.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.shevyakhov.feature.main.MainDestination
import com.shevyakhov.libraries.navigation.FragmentDestination
import com.shevyakhov.libraries.navigation.MainRouter
import kotlinx.coroutines.flow.MutableStateFlow

class MainRouterImpl(
	private val router: Router,
) : MainRouter {

	override val currentScreen = MutableStateFlow<FragmentDestination>(MainDestination)

	private val backStack = mutableListOf<FragmentDestination>()

	override fun open(fragmentDestination: FragmentDestination) {
		backStack.remove(fragmentDestination)
		backStack.add(fragmentDestination)
		currentScreen.value = fragmentDestination
		router.navigateTo(
			CreateInstanceFragmentScreen(fragmentDestination)
		)
	}

	override fun exit() {
		backStack.removeLast()
		if (backStack.isNotEmpty()) {
			open(backStack.last())
		} else {
			router.finishChain()
		}
	}

	override fun clearBackStack() {
		backStack.clear()
	}

	override fun replace(fragmentDestination: FragmentDestination) {
		router.replaceScreen(
			CreateInstanceFragmentScreen(fragmentDestination)
		)
	}

	override fun newRoot(fragmentDestination: FragmentDestination) {
		router.newRootScreen(
			CreateInstanceFragmentScreen(fragmentDestination)
		)
	}

	override fun popToRoot() {
		router.backTo(null)
	}

	override fun finish() {
		router.finishChain()
	}

	override fun popTo(fragmentDestinationClass: Class<out FragmentDestination>) {
		router.backTo(
			BackToFragmentScreen(fragmentDestinationClass.name)
		)
	}
}

private class CreateInstanceFragmentScreen(
	private val fragmentDestination: FragmentDestination,
) : FragmentScreen {

	override val screenKey: String = fragmentDestination::class.java.name

	override fun createFragment(factory: FragmentFactory): Fragment =
		fragmentDestination.createInstance()
}

private class BackToFragmentScreen(override val screenKey: String) : Screen