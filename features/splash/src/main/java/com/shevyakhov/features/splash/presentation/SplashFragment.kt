package com.shevyakhov.features.splash.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shevyakhov.feature.splash.databinding.FragmentSplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment() {

	private val viewModel: SplashViewModel by viewModel()
	private var _binding: FragmentSplashBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentSplashBinding.inflate(inflater, container, false)
		return binding.root
	}


	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}