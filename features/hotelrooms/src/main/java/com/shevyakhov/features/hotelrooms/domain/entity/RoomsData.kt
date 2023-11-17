package com.shevyakhov.features.hotelrooms.domain.entity

import com.google.gson.annotations.SerializedName

data class RoomsData(
	@SerializedName("rooms") var rooms: List<Rooms> = listOf(),
)

data class Rooms(
	@SerializedName("id") var id: Int? = null,
	@SerializedName("name") var name: String? = null,
	@SerializedName("price") var price: Int? = null,
	@SerializedName("price_per") var pricePer: String? = null,
	@SerializedName("peculiarities") var peculiarities: List<String> = listOf(),
	@SerializedName("image_urls") var imageUrls: List<String> = listOf(),
)