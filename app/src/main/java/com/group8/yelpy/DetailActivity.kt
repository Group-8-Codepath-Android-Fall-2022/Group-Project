package com.group8.yelpy

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import java.net.Inet4Address

private const val TAG = "DetailActivity"

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

        val restaurant = intent.getSerializableExtra(RESTAURANT_EXTRA) as Restaurant

        nameView.text = restaurant.name
        ratingBar = findViewById(R.id.rating)
        addressView = findViewById(R.id.address)
        distanceView = findViewById(R.id.distance)
        categoryView = findViewById(R.id.category)
        priceView = findViewById(R.id.price)
        isOpenView = findViewById(R.id.isOpen)
        phoneView = findViewById(R.id.phone)
        websiteView = findViewById(R.id.website)
        healthScoreView = findViewById(R.id.healthScore)

        // Load the media image
        Glide.with(this)
            .load(restaurant.imageUrl)
            .into(imageView)
    }
}