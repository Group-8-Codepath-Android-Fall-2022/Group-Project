package com.group8.yelpy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "DetailFragment"
private const val API_KEY = BuildConfig.API_KEY
private const val BASE_URL =
    "https://api.yelp.com/v3/"

class DetailFragment : Fragment() {

    private lateinit var  restaurant: SingleSearchResult;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        val bundle = this.arguments
        if (bundle != null) {
            val id = bundle.getString("id")
            fetchRestaurant(id!!, view)
        }
        return view
    }

    private fun displayDetails(view: View) {
        val image: ImageView = view.findViewById(R.id.pfp)
        val name: TextView = view.findViewById(R.id.email)
        val address: TextView = view.findViewById(R.id.res_address)
        val category: TextView = view.findViewById(R.id.res_category)
        val price: TextView = view.findViewById(R.id.res_price)
        val rating: RatingBar = view.findViewById(R.id.res_rating)
        val status: TextView = view.findViewById(R.id.status)
        val phone: TextView = view.findViewById(R.id.phone)
        val info: Button = view.findViewById(R.id.info)

        Glide.with(this.requireContext())
            .load(restaurant.imageUrl)
            .apply(RequestOptions().transform(MultiTransformation(CenterCrop(), RoundedCorners(20))))
            .into(image)

        name.text = restaurant.name
        address.text = restaurant.location.displayLocation()
        category.text = restaurant.displayCategories()
        price.text = restaurant.price
        rating.rating = restaurant.rating.toFloat()
        if (restaurant.isClosed) {
            status.text = "Closed"
        } else {
            status.text = "Opening"
        }
        phone.text = restaurant.phone
        info.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(restaurant.infoUrl)
            startActivity(openURL)
        }
    }

    private fun fetchRestaurant(id: String, view: View) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val yelpService = retrofit.create(YelpService::class.java)

        yelpService.getRestaurantsById("Bearer $API_KEY", id).enqueue(object :
            Callback<SingleSearchResult> {
            override fun onResponse(
                call: Call<SingleSearchResult>,
                response: Response<SingleSearchResult>
            ) {
                Log.i(TAG, "onResponse ${response.body()}")
                val body = response.body()
                if (body == null) {
                    Log.w(TAG, "Response body is null")
                    return
                }
                restaurant = body
                displayDetails(view)
            }

            override fun onFailure(call: Call<SingleSearchResult>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }
        })
    }
}