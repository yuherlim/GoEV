package com.example.goev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.goev.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
         // Testing


        //temporarily test button
        val button = findViewById<Button>(R.id.tkTest)
        button.setOnClickListener {
            val intent = Intent(this, TipsAndKnowledge::class.java)
            startActivity(intent)
        }
    }

}