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

        // Find the button by its ID
        val btnSecondActivity = findViewById<Button>(R.id.tester)

        // Set an OnClickListener to navigate to the SecondActivity
        btnSecondActivity.setOnClickListener {
            val intent = Intent(this, TipsAndKnowledge::class.java)
            startActivity(intent)
        }
    }
}