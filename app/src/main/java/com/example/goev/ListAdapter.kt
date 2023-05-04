package com.example.goev

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.goev.databases.TkPostData



class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var postList = emptyList<TkPostData>()
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

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
        holder.itemView.findViewById<ImageView>(R.id.postThumbnail).setImageResource(R.drawable.test)
        holder.itemView.findViewById<TextView>(R.id.contentPreview).text = currentItem.content
        holder.itemView.findViewById<TextView>(R.id.totalLikes).text = currentItem.totalLikes.toString()
        holder.itemView.findViewById<TextView>(R.id.totalDislikes).text = currentItem.totalDislikes.toString()
        holder.itemView.findViewById<TextView>(R.id.totalComments).text = currentItem.totalComments.toString()
    }

    fun setData(post: List<TkPostData>){
        this.postList = post
        notifyDataSetChanged()
    }
}