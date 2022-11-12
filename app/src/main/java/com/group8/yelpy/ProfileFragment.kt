package com.group8.yelpy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers.Main

private const val TAG = "DetailFragment"

class ProfileFragment : Fragment()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val info: Button = view.findViewById(R.id.logOutBtn)
        info.setOnClickListener {
            logOut()
        }
        return view
    }

    private fun logOut() {
        Toast.makeText(getActivity(), "Bye!", Toast.LENGTH_LONG).show()

        // Move to next activity
        val intent = Intent (getActivity(), MainActivity::class.java)
        getActivity()?.startActivity(intent)
    }
}