package com.shevyakhov.feature.main.domain.entity

import com.google.gson.annotations.SerializedName

data class HotelData(

	@SerializedName("id") var id: Int? = null,
	@SerializedName("name") var name: String? = null,
	@SerializedName("adress") var adress: String? = null,
	@SerializedName("minimal_price") var minimalPrice: Int? = null,
	@SerializedName("price_for_it") var priceForIt: String? = null,
	@SerializedName("rating") var rating: Int? = null,
	@SerializedName("rating_name") var ratingName: String? = null,
	@SerializedName("image_urls") var imageUrls: ArrayList<String> = arrayListOf(),
	@SerializedName("about_the_hotel") var aboutTheHotel: AboutTheHotel? = AboutTheHotel(),

	)

data class AboutTheHotel(

	@SerializedName("description") var description: String? = null,
	@SerializedName("peculiarities") var peculiarities: ArrayList<String> = arrayListOf(),
)