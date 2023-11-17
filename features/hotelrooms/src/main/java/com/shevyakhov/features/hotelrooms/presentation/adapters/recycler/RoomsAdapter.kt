package com.shevyakhov.features.hotelrooms.presentation.adapters.recycler

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayoutMediator
import com.shevyakhov.features.hotelrooms.R
import com.shevyakhov.features.hotelrooms.databinding.RoomItemBinding
import com.shevyakhov.features.hotelrooms.domain.entity.Room
import com.shevyakhov.features.hotelrooms.presentation.adapters.viewpager.SliderItem
import com.shevyakhov.features.hotelrooms.presentation.adapters.viewpager.ViewPagerAdapter

class RoomsAdapter(private val onClick: (id: Int) -> Unit) : RecyclerView.Adapter<RoomsAdapter.Holder>() {

	var rooms = mutableListOf<Room>()
	fun setNewData(newData: List<Room>) {
		rooms.clear()
		rooms.addAll(newData)
		notifyDataSetChanged()
	}

	inner class Holder(private val binding: RoomItemBinding) : RecyclerView.ViewHolder(binding.root) {

		fun bind(item: Room) {
			with(binding) {
				val adapter = ViewPagerAdapter()
				hotelImagesVp.adapter = adapter
				roomName.text = item.name
				TabLayoutMediator(tabLayout, hotelImagesVp) { _, _ -> }.attach()
				adapter.initList(item.imageUrls.map { SliderItem(it) })
				minimalPrice.text = buildString {
					clear()
					append(root.resources.getString(R.string.from))
					append(" ")
					append(item.price)
					append(END_SYMBOL)
				}
				priceForIt.text = item.pricePer


				chipsPeculiarities.apply {
					this.removeAllViews()
					item.peculiarities.forEach { chipName ->
						this.addView(createChip(chipName, root.context))
					}
					this.addView(
						createColoredChip(
							root.resources.getString(R.string.more_info),
							root.context
						)
					)
				}
				buttonChooseRoom.setOnClickListener {
					onClick.invoke(item.id ?: throw NotFoundException())
				}
			}
		}

		private fun createChip(name: String, context: Context): Chip {
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

		private fun createColoredChip(name: String, context: Context): Chip {
			return Chip(context).apply {
				text = name
				setChipBackgroundColorResource(R.color.light_blue)
				closeIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow)
				isCloseIconVisible = true
				isCheckable = false
				setCloseIconTintResource(R.color.blue)
				isClickable = false
				setTextColor(ContextCompat.getColor(context, R.color.blue))
				setTextAppearance(R.style.ChipTextAppearance)
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
		val binding = RoomItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return Holder(binding)
	}

	override fun getItemCount(): Int = rooms.size

	override fun onBindViewHolder(holder: Holder, position: Int) {
		holder.bind(rooms[position])
		holder.setIsRecyclable(false)
	}

	interface RoomsOnClick {

		fun invoke(id: Int)
	}

	companion object {

		const val END_SYMBOL = "₽"
	}
}