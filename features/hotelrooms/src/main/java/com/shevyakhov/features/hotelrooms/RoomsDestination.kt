package com.shevyakhov.features.hotelrooms

import androidx.fragment.app.Fragment
import com.shevyakhov.features.hotelrooms.presentation.RoomsFragment
import com.shevyakhov.libraries.navigation.FragmentDestination

class RoomsDestination(private val hotelName: String) : FragmentDestination {

	override fun createInstance(): Fragment =
		RoomsFragment.newInstance(hotelName)
}