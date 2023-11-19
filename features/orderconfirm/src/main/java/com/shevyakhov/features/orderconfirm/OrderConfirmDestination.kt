package com.shevyakhov.features.orderconfirm

import androidx.fragment.app.Fragment
import com.shevyakhov.features.orderconfirm.presentation.OrderConfirmFragment
import com.shevyakhov.libraries.navigation.FragmentDestination

class OrderConfirmDestination(private val order: String) : FragmentDestination {

	/**
	* order is not used, but common sense should be like that
	* */
	override fun createInstance(): Fragment =
		OrderConfirmFragment()
}