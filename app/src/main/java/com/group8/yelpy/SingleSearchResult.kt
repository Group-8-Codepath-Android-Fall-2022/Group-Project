package com.group8.yelpy

import com.google.gson.annotations.SerializedName

data class SingleSearchResult(
    @SerializedName("name") val name: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("is_closed") val isClosed: Boolean,
    @SerializedName("url") val infoUrl: String,
    @SerializedName("display_phone") val phone: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("price") val price: String,
    @SerializedName("location") val location: DetailLocation,
    @SerializedName("photos") val photos: List<String>,
    @SerializedName("categories") val categories: List<Category>
)  {
    fun displayCategories(): String {
        var displayResult = ""
        categories.forEach {
            category ->  displayResult += category.title
        }
        return displayResult
    }
}

data class DetailLocation(
    @SerializedName("display_address") val address: List<String>,
) {
    fun displayLocation(): String {
        return address.joinToString(", ")
    }
}