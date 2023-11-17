package com.shevyakhov.feature.main.presentation.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shevyakhov.feature.main.R
import com.shevyakhov.feature.main.databinding.SliderItemBinding

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.SliderHolder>() {

	private var sliderList = listOf<SliderItem>()

	class SliderHolder(view: View) : RecyclerView.ViewHolder(view) {

		private val sliderItemBinding = SliderItemBinding.bind(view)
		fun bind(item: SliderItem) = with(sliderItemBinding) {
			Glide
				.with(this.root)
				.load(item.imageUrl)
				.centerCrop()
				.placeholder(R.drawable.load)
				.into(image)
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.slider_item, parent, false)
		return SliderHolder(view)
	}

	override fun onBindViewHolder(holder: SliderHolder, position: Int) {
		holder.bind(sliderList[position])
	}

	override fun getItemCount(): Int {
		return sliderList.size
	}

	fun initList(list: List<SliderItem>) {
		sliderList = list
		notifyDataSetChanged()
	}
}