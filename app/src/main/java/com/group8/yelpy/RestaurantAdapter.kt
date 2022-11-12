package com.group8.yelpy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


class RestaurantAdapter(private val context: Context, private val restaurants: List<Restaurant>, private val mListener: OnListFragmentInteractionListener?) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.restaurant, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = restaurants.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mRestaurant: Restaurant? = null
        val mRestaurantImage: ImageView = itemView.findViewById(R.id.image)
        val mRestaurantName: TextView = itemView.findViewById(R.id.name)
        val mRestaurantRating: RatingBar = itemView.findViewById(R.id.rating)
        val mRestaurantReviewCount: TextView = itemView.findViewById(R.id.reviewCount)
        val mRestaurantDistance: TextView = itemView.findViewById(R.id.distance)
        val mRestaurantPrice: TextView = itemView.findViewById(R.id.price)
        val mRestaurantAddress: TextView = itemView.findViewById(R.id.address)
        val mRestaurantCategory: TextView = itemView.findViewById(R.id.category)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]

        holder.mRestaurant = restaurant
        holder.mRestaurantName.text = restaurant.name
        holder.mRestaurantRating.rating = restaurant.rating.toFloat()
        holder.mRestaurantReviewCount.text = "${restaurant.numReviews} Reviews"
        holder.mRestaurantDistance.text = restaurant.displayDistance()
        holder.mRestaurantPrice.text = restaurant.price
        holder.mRestaurantAddress.text = restaurant.location.address
        holder.mRestaurantCategory.text = restaurant.categories[0].title

        Glide.with(context)
            .load(restaurant.imageUrl)
            .apply(RequestOptions().transform(MultiTransformation(CenterCrop(), RoundedCorners(20))))
            .into(holder.mRestaurantImage)

        holder.itemView.setOnClickListener {
            holder.mRestaurant?.let { restaurant ->
                mListener?.onItemClick(restaurant)
            }
        }
    }

}