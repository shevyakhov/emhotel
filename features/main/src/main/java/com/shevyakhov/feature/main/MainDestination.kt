package com.shevyakhov.feature.main

import androidx.fragment.app.Fragment
import com.shevyakhov.feature.main.presentation.MainFragment
import com.shevyakhov.libraries.navigation.FragmentDestination

object MainDestination : FragmentDestination {

	override fun createInstance(): Fragment =
		MainFragment()
}