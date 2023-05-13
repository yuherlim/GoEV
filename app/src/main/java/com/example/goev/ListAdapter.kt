package com.example.goev

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.goev.databases.post.TkPostData
import com.example.goev.databases.tempUser.UserViewModel


class ListAdapter(private val contextFromParent: Context): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var postList = emptyList<TkPostData>()
    private var searchedList = MutableLiveData<List<TkPostData>>()
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val cardView: CardView = itemView.findViewById(R.id.tkCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tips_and_knowledge_card_view,parent,false))
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = postList[position]
        holder.itemView.findViewById<TextView>(R.id.title).text = currentItem.title
        val bitmap = TkImageConverter().extractImage(currentItem.thumbnail!!)
        holder.itemView.findViewById<ImageView>(R.id.postThumbnail).setImageBitmap(bitmap)
        holder.itemView.findViewById<TextView>(R.id.contentPreview).text = currentItem.content
        holder.itemView.findViewById<TextView>(R.id.totalLikes).text = currentItem.totalLikes.toString()
        holder.itemView.findViewById<TextView>(R.id.totalDislikes).text = currentItem.totalDislikes.toString()
        holder.itemView.findViewById<TextView>(R.id.totalComments).text = currentItem.totalComments.toString()

        //transfer data from fragment to activity
        holder.cardView.setOnClickListener {
            val intent = Intent(contextFromParent, TkPostContent::class.java)
            intent.putExtra("postId",currentItem.id)
            intent.putExtra("thumbnail",currentItem.thumbnail)
            intent.putExtra("postTitle",currentItem.title)
            intent.putExtra("postContent",currentItem.content)
            intent.putExtra("postTotalLikes",currentItem.totalLikes)
            intent.putExtra("postTotalDislikes",currentItem.totalDislikes)
            intent.putExtra("postTotalComments",currentItem.totalComments)
            intent.putExtra("postDate", currentItem.postDate.time)
            contextFromParent.startActivities(arrayOf(intent))
        }
    }

    fun setData(post: List<TkPostData>){
        this.postList = post
        notifyDataSetChanged()
    }

    fun setSearchData(post: MutableLiveData<List<TkPostData>>) {
        this.searchedList.value = post.value
        notifyDataSetChanged()
    }
}