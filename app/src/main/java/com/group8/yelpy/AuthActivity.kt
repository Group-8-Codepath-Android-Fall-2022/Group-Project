package com.group8.yelpy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var authButton: Button
    private lateinit var authType: String
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        authButton = findViewById(R.id.authButton)
        authType = intent.getStringExtra("authType")!!
        auth = Firebase.auth

        emailEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)

        authButton.isEnabled = false
        if (authType == "login") {
            authButton.text = "Log in"
        } else {
            authButton.text = "Sign up"
        }

        authButton.setOnClickListener {
            // hide keyboard
            this.currentFocus?.let { view ->
                val imm =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            // log in or sign up
            authenticate()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        // enable button only if no empty input field
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            authButton.isEnabled =
                !(emailEditText.text.toString().isEmpty() || passwordEditText.text.isEmpty())
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    private fun authenticate() {
        val email: String = emailEditText.text.toString()
        val password: String = passwordEditText.text.toString()

        if (authType == "login") {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        logIn()
                    } else {
                        Toast.makeText(this, "Login Failed. Try Again", Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        logIn()
                    } else {
                        Toast.makeText(this, "Signup Failed. Try Again", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun logIn() {
        Toast.makeText(this, "Welcome!", Toast.LENGTH_LONG).show()

        // Move to next activity
        val intent = Intent(this, NavigationActivity::class.java)
        startActivity(intent)
    }
}