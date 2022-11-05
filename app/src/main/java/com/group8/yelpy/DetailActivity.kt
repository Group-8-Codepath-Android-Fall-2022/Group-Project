package com.group8.yelpy

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.group8.yelpy.BuildConfig.API_KEY
import okhttp3.Headers
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Inet4Address

private const val TAG = "DetailActivity"
private const val API_KEY = BuildConfig.API_KEY
private const val BASE_URL =
    "https://api.yelp.com/v3/"

class DetailActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var nameView: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var addressView: TextView
    private lateinit var distanceView: TextView
    private lateinit var categoryView: TextView
    private lateinit var priceView: TextView
    private lateinit var isOpenView: TextView
    private lateinit var phoneView: TextView
    private lateinit var websiteView: TextView
    private lateinit var healthScoreView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        imageView = findViewById(R.id.image)
        nameView = findViewById(R.id.name)
        ratingBar = findViewById(R.id.rating)
        addressView = findViewById(R.id.address)
        distanceView = findViewById(R.id.distance)
        categoryView = findViewById(R.id.category)
        priceView = findViewById(R.id.price)
        isOpenView = findViewById(R.id.isOpen)
        phoneView = findViewById(R.id.phone)
        websiteView = findViewById(R.id.website)
        healthScoreView = findViewById(R.id.healthScore)


//        val restaurant = intent.getSerializableExtra(RESTAURANT_EXTRA) as Restaurant
        val id = intent.getSerializableExtra(RESTAURANT_EXTRA) as String

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val yelpService = retrofit.create(YelpService::class.java)

        yelpService.getRestaurantById("Bearer $API_KEY", id).enqueue(object :
            Callback<YelpSearchResult> {
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                Log.i(TAG, "onResponse $response")
                val body = response.body()
                if (body == null) {
                    Log.w(TAG, "Response body is null")
                    return
                }

                nameView.text = body.restaurants[0].name
                ratingBar.rating = body.restaurants[0].rating.toFloat()
                addressView.text = body.restaurants[0].location.address
                distanceView.text = body.restaurants[0].displayDistance()
                categoryView.text = body.restaurants[0].categories[0].title
                priceView.text = body.restaurants[0].price
                isOpenView.text = body.restaurants[0].is_open_now.toString()
                phoneView.text = body.restaurants[0].phone
                websiteView.text = body.restaurants[0].url
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }
        })



//        val client = AsyncHttpClient()
//        val params = RequestParams()

//        client[
//                "https://api.yelp.com/v3/businesses/".plus(id.toString()),
//                params,
//                object : JsonHttpResponseHandler()
//
//                {
//                    /*
//                     * The onSuccess function gets called when
//                     * HTTP response status is "200 OK"
//                     */
//                    override fun onSuccess(
//                        statusCode: Int,
//                        headers: Headers,
//                        json: JsonHttpResponseHandler.JSON
//                    ) {

//                        val resultsJSON = json.jsonObject?.getJSONArray("results") as JSONArray
//                        val restaurantRawJSON : String = resultsJSON.toString()
//                        val gson = Gson()
//                        val arrayRType = object : TypeToken<List<Restaurant>>() {}.type
//                        val model : Restaurant = gson.fromJson(restaurantRawJSON, arrayRType)

//                        nameView.text = model.name
//                        ratingBar.rating = model.rating.toFloat()
//                        addressView.text = model.location.address
//                        distanceView.text = model.displayDistance()
//                        categoryView.text = model.categories[0].title
//                        priceView.text = model.price
//                        isOpenView.text = model.is_open_now.toString()
//                        phoneView.text = model.phone
//                        websiteView.text = model.url

//                        // Load the media image
//                        Glide.with(this@DetailActivity)
//                            .load(model.imageUrl)
//                            .into(imageView)
//
//                        // Look for this in Logcat:
//                        Log.d("Detail API call", "response successful")
//
//                    }
//
//                    /*
//                     * The onFailure function gets called when
//                     * HTTP response status is "4XX" (eg. 401, 403, 404)
//                     */
//                    override fun onFailure(
//                        statusCode: Int,
//                        headers: Headers?,
//                        errorResponse: String,
//                        t: Throwable?
//                    ) {
//
//                        // If the error is not null, log it!
//                        t?.message?.let {
//                            Log.e("Detail API call", errorResponse)
//                        }
//                    }
//                }]



    }
}