package com.group8.yelpy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


class RestaurantAdapter(private val context: Context, private val restaurants: List<Restaurant>) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.restaurant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.bind(restaurant)
    }

    override fun getItemCount() = restaurants.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val mRestaurantImage: ImageView = itemView.findViewById(R.id.image)
        private val mRestaurantName: TextView = itemView.findViewById(R.id.name)
        private val mRestaurantRating: RatingBar = itemView.findViewById(R.id.rating)
        private val mRestaurantReviewCount: TextView = itemView.findViewById(R.id.reviewCount)
        private val mRestaurantDistance: TextView = itemView.findViewById(R.id.distance)
        private val mRestaurantPrice: TextView = itemView.findViewById(R.id.price)
        private val mRestaurantAddress: TextView = itemView.findViewById(R.id.address)
        private val mRestaurantCategory: TextView = itemView.findViewById(R.id.category)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(restaurant: Restaurant) {
            mRestaurantName.text = restaurant.name
            mRestaurantRating.rating = restaurant.rating.toFloat()
            mRestaurantReviewCount.text = "${restaurant.numReviews} Reviews"
            mRestaurantDistance.text = restaurant.displayDistance()
            mRestaurantPrice.text = restaurant.price
            mRestaurantAddress.text = restaurant.location.address
            mRestaurantCategory.text = restaurant.categories[0].title

            Glide.with(context)
                .load(restaurant.imageUrl)
                .apply(RequestOptions().transform(MultiTransformation(CenterCrop(), RoundedCorners(20))))
                .into(mRestaurantImage)
        }

        override fun onClick(v: View?) {
            // Get selected article
//            val restaurant = restaurants[]

//             Navigate to Details screen and pass selected article
//            val intent = Intent(context, DetailActivity::class.java)
//            intent.putExtra(ARTICLE_EXTRA, article)
//            context.startActivity(intent)
        }
    }
}