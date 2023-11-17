package com.shevyakhov.features.hotelrooms

import androidx.fragment.app.Fragment
import com.shevyakhov.features.hotelrooms.presentation.RoomsFragment
import com.shevyakhov.libraries.navigation.FragmentDestination

object RoomsDestination : FragmentDestination {

	override fun createInstance(): Fragment =
		RoomsFragment()
}