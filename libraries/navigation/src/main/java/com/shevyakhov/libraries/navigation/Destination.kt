package com.shevyakhov.libraries.navigation

import androidx.fragment.app.Fragment

sealed interface Destination

interface FragmentDestination : Destination {

	fun createInstance(): Fragment
}