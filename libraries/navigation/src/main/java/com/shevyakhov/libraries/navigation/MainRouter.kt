package com.shevyakhov.libraries.navigation

import kotlinx.coroutines.flow.StateFlow

interface MainRouter {

	val currentScreen: StateFlow<FragmentDestination>
	fun open(fragmentDestination: FragmentDestination)
	fun exit()
	fun clearBackStack()

	fun replace(fragmentDestination: FragmentDestination)
	fun newRoot(fragmentDestination: FragmentDestination)
	fun popToRoot()
	fun finish()
	fun popTo(fragmentDestinationClass: Class<out FragmentDestination>)
}