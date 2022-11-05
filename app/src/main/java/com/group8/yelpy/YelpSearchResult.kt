package com.group8.yelpy

import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName

data class YelpSearchResult(
    @SerializedName("total") val total: Int,
    @SerializedName("businesses") val restaurants: List<Restaurant>
)

data class Restaurant (
    val name: String,
    val rating: Double,
    @SerializedName("review_count") val numReviews: Int,
    val price: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("distance") val distanceInMeters: Double,
    val categories: List<Category>,
    val location: Location,
    val phone: String,
    val url: String,
    val id: String,
    val is_open_now: Boolean
    ) {
    fun displayDistance(): String {
        val distanceInMiles = "%.2f".format(distanceInMeters * 0.000621371)
        return "$distanceInMiles mi"
    }
}

data class Category(
    val title: String
)

data class Location(
    @SerializedName("address1") val address: String,
)