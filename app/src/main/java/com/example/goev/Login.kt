package com.example.goev

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.goev.database.User
import com.example.goev.database.UserDao
import com.example.goev.database.UserDatabase
import com.example.goev.databinding.ActivityLoginBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var userDb : UserDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.loginButton.setOnClickListener {

            checkUser()

        }

    }
    private fun checkUser() {

        // Get user input
        val email = binding.editLoginEmail.text.toString()
        val password = binding.editLoginPassword.text.toString()

        // Validate user input
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
        } else {

            lateinit var user : User

            GlobalScope.launch {
                user = userDb.userDao().getUserByEmail(email)!!
                if (user.password == password) {
                    // Navigate to main activity



                } else {
                    //Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}