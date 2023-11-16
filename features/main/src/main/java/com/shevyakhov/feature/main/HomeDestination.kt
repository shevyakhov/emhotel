package com.shevyakhov.feature.main

import androidx.fragment.app.Fragment
import com.shevyakhov.feature.main.presentation.HomeFragment
import com.shevyakhov.libraries.navigation.FragmentDestination

object HomeDestination : FragmentDestination {

	override fun createInstance(): Fragment =
		HomeFragment()
}