package com.example.goev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.goev.databinding.ActivityMainBinding

class TipsAndKnowledge: AppCompatActivity() {
    lateinit var binding: TipsAndKnowledge
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tips_and_knowledge)
        val recyclerView = findViewById<RecyclerView>(R.id.cardRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val post1 = tkPost(1, "testingTitle1",R.drawable.test,"This is content preview testing 1"
        , 50,50,50)
        tkPostList.add(post1)
        val post2 = tkPost(2, "testingTitle2",R.drawable.test,"This is content preview testing 2"
            , 50,50,50)
        tkPostList.add(post2)

        recyclerView.adapter = ktAdapter(tkPostList)

    }
}