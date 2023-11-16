package com.shevyakhov.features.splash

import androidx.fragment.app.Fragment
import com.shevyakhov.features.splash.presentation.SplashFragment
import com.shevyakhov.libraries.navigation.FragmentDestination

class SplashDestination : FragmentDestination {

	override fun createInstance(): Fragment = SplashFragment()
}