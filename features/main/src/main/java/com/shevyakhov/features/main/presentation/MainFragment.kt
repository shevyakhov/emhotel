package com.shevyakhov.features.main.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayoutMediator
import com.shevyakhov.features.main.R
import com.shevyakhov.features.main.databinding.FragmentMainBinding
import com.shevyakhov.features.main.presentation.viewpager.SliderItem
import com.shevyakhov.features.main.presentation.viewpager.ViewPagerAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

	companion object {

		private const val LOG = "MAINFRAGMENT"
	}

	private val viewModel: MainFragmentViewModel by viewModel()
	private var _binding: FragmentMainBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentMainBinding.inflate(inflater, container, false)
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
			val ratingColors = resources.getIntArray(R.array.rating_color)
			val ratingTextColors = resources.getIntArray(R.array.rating_color_text)
			val rating = state.hotelData.rating ?: 0

			val color = ratingColors[rating.mod(ratingColors.size)]
			val textColor = ratingTextColors[rating.mod(ratingTextColors.size)]

			val adapter = ViewPagerAdapter()
			hotelImagesVp.adapter = adapter

			TabLayoutMediator(tabLayout, hotelImagesVp) { _, _ -> }.attach()


			adapter.initList(state.hotelData.imageUrls.map { SliderItem(it) })
			ratingLayout.setBackgroundColor(color)
			star.setColorFilter(textColor)

			hotelRating.setTextColor(textColor)
			hotelRating.text = rating.toString()
			hotelRatingName.setTextColor(textColor)
			hotelRatingName.text = state.hotelData.ratingName.toString()

			hotelName.text = state.hotelData.name
			hotelAddress.text = state.hotelData.adress
			hotelName.text = state.hotelData.name

			minimalPrice.text = buildString {
				clear()
				append(getString(R.string.from))
				append(" ")
				append(state.hotelData.minimalPrice)
				append(" ₽")
			}
			priceForIt.text = state.hotelData.priceForIt

			hotelDescription.text = state.hotelData.aboutTheHotel?.description
			chipsPeculiarities.apply {
				this.removeAllViews()
				state.hotelData.aboutTheHotel?.peculiarities?.forEach { chipName ->
					this.addView(createChip(chipName))
				}
			}

		}
	}

	private fun renderError(state: UiState.Error) {
		Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_SHORT).show()
		viewModel.retryLoading()
		Log.e(LOG, state.e.stackTraceToString())
	}

	private fun createChip(name: String): Chip {
		return Chip(context).apply {
			text = name
			setChipBackgroundColorResource(R.color.surface)
			isCloseIconVisible = false
			isCheckable = false
			isClickable = false
			setTextColor(ContextCompat.getColor(context, R.color.dark_grey))
			setTextAppearance(R.style.ChipTextAppearance)
		}

	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}