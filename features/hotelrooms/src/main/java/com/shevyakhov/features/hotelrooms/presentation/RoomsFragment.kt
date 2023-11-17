package com.shevyakhov.features.hotelrooms.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.shevyakhov.features.hotelrooms.R
import com.shevyakhov.features.hotelrooms.databinding.FragmentRoomsBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class RoomsFragment : Fragment() {

	companion object {

		private const val LOG = "ROOMSFRAGMENT"
	}

	private val viewModel: RoomsViewModel by viewModel()
	private var _binding: FragmentRoomsBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentRoomsBinding.inflate(inflater, container, false)
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
			Log.e(LOG,state.toString())
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