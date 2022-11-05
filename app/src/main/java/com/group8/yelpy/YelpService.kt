package com.group8.yelpy

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface YelpService {

    @GET("businesses/search")
    fun getRestaurantsByLocation(
        @Header("Authorization") authHeader: String,
        @Query("location") location: String
    ) : Call<YelpSearchResult>

    @GET("businesses/search")
    fun getRestaurantsByTerm(@Query("term") term: String) : Call<YelpSearchResult>

    @GET("businesses/search")
    fun getRestaurantsByLatitudeAndLongitude(
        @Header("Authorization") authHeader: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
    ) : Call<YelpSearchResult>

    @GET("businesses/{id}")
    fun getRestaurantsById(
        @Header("Authorization") authHeader: String,
        @Path("id") restaurantId: String,
    ) : Call<SingleSearchResult>

}