package com.shevyakhov.features.orderconfirm.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.shevyakhov.features.orderconfirm.R
import com.shevyakhov.features.orderconfirm.databinding.FragmentOrderConfirmBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderConfirmFragment : Fragment() {

	companion object {

		private const val LOG = "ORDER_FRAGMENT"
	}

	private val viewModel: OrderConfirmViewModel by viewModel()
	private var _binding: FragmentOrderConfirmBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentOrderConfirmBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		bindData()
		viewModel.loadData()
	}

	private fun bindData() {
		val scope = viewLifecycleOwner.lifecycleScope

		binding.buttonBack.setOnClickListener {
			viewModel.navigateBack()
		}

		binding.navigateToHome.setOnClickListener {
			viewModel.navigateToHomeScreen()
		}
		viewModel.uiState.onEach { state ->
			when (state) {
				is UiState.Content -> renderContent(state)
				is UiState.Error   -> renderError(state)
				is UiState.Initial -> Unit
			}
		}.launchIn(scope)
	}

	private fun renderError(state: UiState.Error) {
		Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_SHORT).show()
		viewModel.retryLoading()
		Log.e(LOG, state.e.stackTraceToString())
	}

	private fun renderContent(state: UiState.Content) {
		binding.orderText.text = buildString {
			clear()
			append(getString(R.string.confirm_text_before))
			append(state.orderNumber)
			append(" ")
			append(getString(R.string.confirm_text_after))
		}

	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}