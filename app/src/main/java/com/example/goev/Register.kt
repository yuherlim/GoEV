package com.example.goev

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.goev.databinding.ActivityRegisterBinding

class Register : AppCompatActivity(){

    private lateinit var binding : ActivityRegisterBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.registerButton.setOnClickListener {

        }
    }
}