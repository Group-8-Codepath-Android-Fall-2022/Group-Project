package com.group8.yelpy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private lateinit var logInButton: Button
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logInButton = findViewById(R.id.logInBtn)
        signUpButton = findViewById(R.id.signUpBtn)

        logInButton.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            intent.putExtra("authType", "login")
            startActivity(intent)
        }

        signUpButton.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            intent.putExtra("authType", "signup");
            startActivity(intent)
        }
    }
}