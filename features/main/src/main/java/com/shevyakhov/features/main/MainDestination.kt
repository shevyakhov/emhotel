package com.shevyakhov.features.main

import androidx.fragment.app.Fragment
import com.shevyakhov.features.main.presentation.MainFragment
import com.shevyakhov.libraries.navigation.FragmentDestination

object MainDestination : FragmentDestination {

	override fun createInstance(): Fragment =
		MainFragment()
}