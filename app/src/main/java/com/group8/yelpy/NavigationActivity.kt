package com.group8.yelpy


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
//import kotlinx.serialization.json.Json
//
//fun createJson() = Json {
//    isLenient = true
//    ignoreUnknownKeys = true
//    useAlternativeNames = false
//}

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val streamFragment: Fragment = StreamFragment()
        val profileFragment: Fragment = ProfileFragment()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

//         handle navigation selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.nav_stream -> fragment = streamFragment
                R.id.nav_me -> fragment = profileFragment
            }
            replaceFragment(fragment)
            true
        }

        replaceFragment(streamFragment)

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.nav_stream
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.yelpy_frame_layout, fragment)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.rlContainer)
        (fragment as? IOnBackPressed)?.onBackPressed()?.not()?.let {
            super.onBackPressed()
        }
    }

}