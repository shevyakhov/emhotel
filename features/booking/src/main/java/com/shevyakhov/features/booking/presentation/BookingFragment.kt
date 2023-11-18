package com.shevyakhov.features.booking.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.shevyakhov.features.booking.R
import com.shevyakhov.features.booking.databinding.FragmentBookingBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookingFragment : Fragment() {

	companion object {

		private const val LOG = "BOOKING_FRAGMENT"
	}

	private val viewModel: BookingViewModel by viewModel()
	private var _binding: FragmentBookingBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentBookingBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		bindData()
	}

	private fun bindData() {
		val scope = viewLifecycleOwner.lifecycleScope
		viewModel.uiState.onEach { state ->
			when (state) {
				is UiState.Content -> renderContent(state)
				is UiState.Error   -> renderError(state)
				is UiState.Initial -> Unit
			}
		}.launchIn(scope)
	}

	private fun renderContent(state: UiState.Content) {
		with(binding) {

		}
	}

	private fun renderError(state: UiState.Error) {
		Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_SHORT).show()
		viewModel.retryLoading()
		Log.e(LOG, state.e.stackTraceToString())
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

}