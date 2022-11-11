package com.group8.yelpy

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val TAG = "StreamFragment"
private const val API_KEY = BuildConfig.API_KEY
private const val BASE_URL =
    "https://api.yelp.com/v3/"


class StreamFragment : Fragment(), LocationListener, OnListFragmentInteractionListener {
    private val restaurants = mutableListOf<Restaurant>()
    private lateinit var restaurantsRecyclerView: RecyclerView
    private lateinit var restaurantAdapter: RestaurantAdapter
    lateinit var searchView: SearchView
    private val locationPermissionCode = 2
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var contain : Boolean = false;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stream, container, false)
        val layoutManager = LinearLayoutManager(context)
        restaurantsRecyclerView = view.findViewById(R.id.restaurant_recycler_view)
        restaurantsRecyclerView.layoutManager = layoutManager
        restaurantsRecyclerView.setHasFixedSize(true)
        restaurantAdapter = RestaurantAdapter(view.context, restaurants, this@StreamFragment)
        restaurantsRecyclerView.adapter = restaurantAdapter
        searchView = view.findViewById(R.id.searchView)
        fetchRestaurants()

        // listener for our search view.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // on below line we are checking
                // if query exist or not.
                for (i in 0 until restaurants.size) {
                    contain = restaurants[i].name.contains(query as CharSequence)
                    if (contain)
                        break
                }
                if (contain) {
                    // if query exist within list we
                    // are filtering our list adapter.
                    restaurants.clear()
                    getAllRestarants()
                    removeNotMatched(query)
                    restaurantAdapter.notifyDataSetChanged()
                } else {
                    // if query is not present we are displaying
                    // a toast message as no  data found..
                    Toast.makeText(context, "No Restaurants found..", Toast.LENGTH_LONG)
                        .show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // if query text is change in that case we
                // are filtering our adapter with
                // new text on below line.
                restaurants.clear()
                getAllRestarants()
                removeNotMatched(newText)
                restaurantAdapter.notifyDataSetChanged()
                return true
            }
        })

        return view
    }

    private fun fetchRestaurants() {

        getLocation()

        // Add all restaurants to the list
        getAllRestarants()

    }

    private fun removeNotMatched(query: String?) {
        var containsKey: Boolean = false
        for (i in 0 until restaurants.size) {
            containsKey = restaurants[i].name.contains(query as CharSequence)
            if (!containsKey)
                restaurants.removeAt(i)
        }
    }

    private fun getAllRestarants() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val yelpService = retrofit.create(YelpService::class.java)

        yelpService.getRestaurantsByLatitudeAndLongitude("Bearer $API_KEY", latitude, longitude).enqueue(object : Callback<YelpSearchResult> {
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                Log.i(TAG, "onResponse $response")
                val body = response.body()
                if (body == null) {
                    Log.w(TAG, "Response body is null")
                    return
                }
                restaurants.addAll(body.restaurants)
                restaurantAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }
        })
    }

    private fun getLocation() {
        var locationManager: LocationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
    }

    override fun onItemClick(item: Restaurant) {
        val bundle = Bundle()
        bundle.putString("id", item.id)
        val detailFragment= DetailFragment()
        detailFragment.arguments = bundle

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.yelpy_frame_layout, detailFragment)
        fragmentTransaction?.commit()
    }

}
