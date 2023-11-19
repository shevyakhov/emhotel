package com.shevyakhov.features.booking

import androidx.fragment.app.Fragment
import com.shevyakhov.features.booking.presentation.BookingFragment
import com.shevyakhov.libraries.navigation.FragmentDestination

object BookingDestination : FragmentDestination {

	override fun createInstance(): Fragment = BookingFragment()
}