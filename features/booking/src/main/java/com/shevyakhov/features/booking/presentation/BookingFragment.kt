package com.shevyakhov.features.booking.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.shevyakhov.features.booking.R
import com.shevyakhov.features.booking.databinding.FragmentBookingBinding
import com.shevyakhov.features.booking.presentation.adapter.TouristAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

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

		binding.buttonBack.setOnClickListener {
			viewModel.navigateBack()
		}
		bindPayerInfo()
		bindAdapter(scope)

		viewModel.uiState.onEach { state ->
			when (state) {
				is UiState.Content -> renderContent(state)
				is UiState.Error   -> renderError(state)
				is UiState.Initial -> Unit
			}
		}.launchIn(scope)
	}

	private fun bindAdapter(scope: LifecycleCoroutineScope) {
		val touristAdapter = TouristAdapter()
		binding.touristsRv.adapter = touristAdapter
		binding.touristsRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

		binding.buttonAddTourist.setOnClickListener {
			viewModel.saveData(touristAdapter.tourists)
			viewModel.addTourist()
		}

		viewModel.touristFlow.onEach {
			touristAdapter.setNewData(it, viewModel.validFlow.value)
		}.launchIn(scope)
		viewModel.validFlow.onEach {
			touristAdapter.setNewData(viewModel.touristFlow.value, viewModel.validFlow.value)
		}.launchIn(scope)


		binding.navigateToPayment.setOnClickListener {
			viewModel.saveData(touristAdapter.tourists)
			viewModel.emailFlow.value = binding.email.text.toString()
			viewModel.phoneFlow.value = binding.telephone.text.toString()
			viewModel.validateFields(touristAdapter.tourists)
		}

		viewModel.errorChannelFlow.onEach {
			binding.emailLayout.error = isEmailValidError()
			binding.telephoneLayout.error = isPhoneValidError()
			Toast.makeText(requireContext(), getString(R.string.validators_error_empty), Toast.LENGTH_SHORT).show()
			touristAdapter.setNewData(viewModel.touristFlow.value, viewModel.validFlow.value)
		}.launchIn(scope)
	}

	private fun bindPayerInfo() {
		binding.email.addTextChangedListener {
			binding.emailLayout.error = null
		}
		binding.telephone.addTextChangedListener {
			binding.telephoneLayout.error = null
		}
		val slots = UnderscoreDigitSlotsParser().parseSlots("+7(___) ___-__-__")
		val mask = MaskImpl.createTerminated(slots)
		mask.placeholder = '*';
		mask.isShowingEmptySlots = true;
		val formatWatcher: FormatWatcher = MaskFormatWatcher(mask)
		formatWatcher.installOn(binding.telephone)
	}

	private fun isEmailValidError() =
		if (viewModel.emailFlow.value.matches(BookingViewModel.EMAIL_REGEX.toRegex())) null else getString(R.string.value_error)

	private fun isPhoneValidError() =
		if (viewModel.phoneFlow.value.length == 17 && !viewModel.phoneFlow.value.contains("*")) null else getString(R.string.value_error)

	private fun renderContent(state: UiState.Content) {
		with(binding) {
			bindHotelData(state)
			bindFlightData(state)
			bindPayment(state)
		}
	}

	private fun FragmentBookingBinding.bindHotelData(
		state: UiState.Content,
	) {
		val ratingColors = resources.getIntArray(R.array.rating_color)
		val ratingTextColors = resources.getIntArray(R.array.rating_color_text)
		val rating = state.bookingData.horating ?: 0

		val color = ratingColors[rating.mod(ratingColors.size)]
		val textColor = ratingTextColors[rating.mod(ratingTextColors.size)]
		hotelRating.setTextColor(textColor)
		hotelRating.text = rating.toString()
		hotelRatingName.setTextColor(textColor)
		hotelRatingName.text = state.bookingData.ratingName.toString()
		ratingLayout.setBackgroundColor(color)
		star.setColorFilter(textColor)

		hotelName.text = state.bookingData.hotelName
		hotelAddress.text = state.bookingData.hotelAdress
	}

	private fun FragmentBookingBinding.bindFlightData(state: UiState.Content) {
		flyFromText.text = state.bookingData.departure
		flyCityText.text = state.bookingData.arrivalCountry
		val date = state.bookingData.tourDateStart + "-" + state.bookingData.tourDateStop
		flyDateText.text = date

		flyDaysText.text = state.bookingData.numberOfNights.toString()

		flyHotelText.text = state.bookingData.hotelName
		flyRoomText.text = state.bookingData.room
		flyEatingText.text = state.bookingData.nutrition
	}

	@SuppressLint("SetTextI18n")
	private fun FragmentBookingBinding.bindPayment(state: UiState.Content) {
		val tour = state.bookingData.tourPrice ?: 0
		val fuel = state.bookingData.fuelCharge ?: 0
		val service = state.bookingData.serviceCharge ?: 0

		paymentTourText.text = "$tour ₽"
		paymentFuelText.text = "$fuel ₽"
		paymentServiceText.text = "$service ₽"

		val totalPrice = tour + fuel + service
		paymentTotalText.text = "$totalPrice ₽"

		binding.navigateToPayment.text = buildString {
			clear()
			append(getString(R.string.button_pay))
			append(" $totalPrice ₽")
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