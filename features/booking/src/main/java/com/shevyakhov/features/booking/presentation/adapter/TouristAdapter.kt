package com.shevyakhov.features.booking.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.shevyakhov.features.booking.R
import com.shevyakhov.features.booking.databinding.ChildItemBinding
import com.shevyakhov.features.booking.databinding.ParentItemBinding
import com.shevyakhov.features.booking.presentation.BookingViewModel
import pokercc.android.expandablerecyclerview.ExpandableAdapter
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class TouristAdapter : ExpandableAdapter<ExpandableAdapter.ViewHolder>() {

	var tourists = mutableListOf<Tourist>()

	private var errorsMap: MutableList<Map<Int, Boolean>> = mutableListOf(
		mapOf(
			NAME to true,
			SURNAME to true,
			BIRTHDAY to true,
			RESIDENCY to true,
			PASSPORT to true,
			PASSPORT_DATE to true
		)
	)

	@SuppressLint("NotifyDataSetChanged")
	fun setNewData(newData: List<Tourist>, newErrors: MutableList<Map<Int, Boolean>>) {
		tourists.clear()
		errorsMap.clear()
		tourists.addAll(newData)
		errorsMap.addAll(newErrors)
		notifyDataSetChanged()
	}

	inner class ChildVH(val binding: ChildItemBinding) : ViewHolder(binding.root) {

		fun bind(groupPosition: Int) {
			val tourist = tourists[groupPosition]
			val errors = errorsMap[groupPosition]

			errors.forEach { (i, b) ->
				when (i) {
					NAME          -> bindNameLayoutError(b)

					SURNAME       -> bindSurnameLayoutError(b)

					BIRTHDAY      -> bindBithdayLayoutError(b)

					RESIDENCY     -> bindResidencyLayoutError(b)

					PASSPORT      -> bindPassportLayoutError(b)

					PASSPORT_DATE -> bindPassportDateLayoutError(b)
				}
			}

			binding.name.setText(tourist.name)
			binding.surname.setText(tourist.surname)
			binding.birthday.setText(tourist.birthday)
			binding.residency.setText(tourist.residency)
			binding.passport.setText(tourist.passport)
			binding.passportDate.setText(tourist.passportExpireDate)

			binding.name.doAfterTextChanged {
				tourists[groupPosition].name = it.toString()
				it?.let {
					bindNameLayoutError(it.isNotBlank())
				}
			}
			binding.surname.doAfterTextChanged {
				tourists[groupPosition].surname = it.toString()
				it?.let { bindSurnameLayoutError(it.isNotBlank()) }
			}
			binding.birthday.doAfterTextChanged {
				tourists[groupPosition].birthday = it.toString()
				it?.let { bindBithdayLayoutError(it.matches(BookingViewModel.DATA_REGEX.toRegex())) }
			}
			binding.residency.doAfterTextChanged {
				tourists[groupPosition].residency = it.toString()
				it?.let { bindResidencyLayoutError(it.isNotBlank()) }
			}
			binding.passport.doAfterTextChanged {
				tourists[groupPosition].passport = it.toString()
				it?.let { bindPassportLayoutError(it.isNotBlank()) }
			}
			binding.passportDate.doAfterTextChanged {
				tourists[groupPosition].passportExpireDate = it.toString()
				it?.let { bindPassportDateLayoutError(it.matches(BookingViewModel.DATA_REGEX.toRegex())) }
			}
			val slotsPassport = UnderscoreDigitSlotsParser().parseSlots("__ ________")
			val passportWatcher: FormatWatcher = MaskFormatWatcher(
				MaskImpl.createTerminated(slotsPassport)
			)
			passportWatcher.installOn(binding.passport)

			val slotsDate = UnderscoreDigitSlotsParser().parseSlots("__.__.____")
			val formatWatcher: FormatWatcher = MaskFormatWatcher(
				MaskImpl.createTerminated(slotsDate)
			)
			val slotsDate2 = UnderscoreDigitSlotsParser().parseSlots("__.__.____")
			val formatWatcher2: FormatWatcher = MaskFormatWatcher(
				MaskImpl.createTerminated(slotsDate2)
			)
			formatWatcher.installOn(binding.passportDate)
			formatWatcher2.installOn(binding.birthday)
		}

		private fun bindPassportDateLayoutError(b: Boolean) {
			binding.passportDateLayout.error = if (b) {
				null
			} else {
				binding.root.context.getString(R.string.value_error)
			}
		}

		private fun bindPassportLayoutError(b: Boolean) {
			binding.passportLayout.error = if (b) {
				null
			} else {
				binding.root.context.getString(R.string.value_error)
			}
		}

		private fun bindResidencyLayoutError(b: Boolean) {
			binding.residencyLayout.error = if (b) {
				null
			} else {
				binding.root.context.getString(R.string.value_error)
			}
		}

		private fun bindBithdayLayoutError(b: Boolean) {
			binding.birthdayLayout.error = if (b) {
				null
			} else {
				binding.root.context.getString(R.string.value_error)
			}
		}

		private fun bindSurnameLayoutError(b: Boolean) {
			binding.surnameLayout.error = if (b) {
				null
			} else {
				binding.root.context.getString(R.string.value_error)
			}
		}

		private fun bindNameLayoutError(b: Boolean) {
			binding.nameLayout.error = if (b) {
				null
			} else {
				binding.root.context.getString(R.string.value_error)
			}
		}

	}

	inner class ParentVH(val binding: ParentItemBinding) : ViewHolder(binding.root)

	override fun onCreateGroupViewHolder(
		viewGroup: ViewGroup,
		viewType: Int,
	): ViewHolder = LayoutInflater.from(viewGroup.context)
		.let { ParentItemBinding.inflate(it, viewGroup, false) }
		.let { ParentVH(it) }

	override fun onCreateChildViewHolder(
		viewGroup: ViewGroup,
		viewType: Int,
	): ViewHolder = LayoutInflater.from(viewGroup.context)
		.let { ChildItemBinding.inflate(it, viewGroup, false) }
		.let { ChildVH(it) }

	override fun getChildCount(groupPosition: Int): Int {
		return 1
	}

	override fun getGroupCount(): Int {
		return tourists.size
	}

	override fun onGroupViewHolderExpandChange(
		holder: ViewHolder,
		groupPosition: Int,
		animDuration: Long,
		expand: Boolean,
	) {
		holder as ParentVH
		val arrowImage = holder.binding.src
		arrowImage.animate()
			.setDuration(animDuration)
			.rotation(if (expand) -180f else 0f)
			.start()
	}

	override fun onBindGroupViewHolder(
		holder: ViewHolder,
		groupPosition: Int,
		expand: Boolean,
		payloads: List<Any>,
	) {
		holder as ParentVH
		val text = holder.binding.root.context.getString(R.string.tourist)
		if (groupPosition > 4)
			holder.binding.touristNumber.text = buildString {
				clear()
				append(groupPosition + 1)
				append("й ")
				append(text)
			}
		else
			holder.binding.touristNumber.text = touristsName[groupPosition] + " $text"

		if (payloads.isEmpty()) {
			val arrowImage = holder.binding.src
			arrowImage.rotation = if (expand) -180f else 0f
		}

	}

	override fun onBindChildViewHolder(
		holder: ViewHolder,
		groupPosition: Int,
		childPosition: Int,
		payloads: List<Any>,
	) {
		holder as ChildVH
		holder.bind(groupPosition)
	}

	companion object {

		val touristsName = listOf("Первый", "Второй", "Третий", "Четвертый", "Пятый")
		const val NAME = 0
		const val SURNAME = 1
		const val BIRTHDAY = 2
		const val RESIDENCY = 3
		const val PASSPORT = 4
		const val PASSPORT_DATE = 5
	}
}