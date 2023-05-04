package com.example.goev

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.goev.databases.TkPostData



class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var postList = emptyList<TkPostData>()
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
        holder.itemView.findViewById<ImageView>(R.id.postThumbnail).setImageResource(R.drawable.test)
        holder.itemView.findViewById<TextView>(R.id.contentPreview).text = currentItem.content
        holder.itemView.findViewById<TextView>(R.id.totalLikes).text = currentItem.totalLikes.toString()
        holder.itemView.findViewById<TextView>(R.id.totalDislikes).text = currentItem.totalDislikes.toString()
        holder.itemView.findViewById<TextView>(R.id.totalComments).text = currentItem.totalComments.toString()

        //pass the cardview current holding data to the into post fragment
        val bundle = Bundle().apply{
            putLong("postId",currentItem.id)
            putString("postTitle",currentItem.title)
            putString("postContent",currentItem.content)
            //pass likes, dislikes and comment in integer, update the number when user perform any of the actions
            putInt("postTotalLikes",currentItem.totalLikes)
            putInt("postTotalDislikes",currentItem.totalDislikes)
            putInt("postTotalComments",currentItem.totalComments)
            putLong("postDate", currentItem.postDate.time)
        }

        holder.cardView.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_tipsAndKnowledge_to_tkIntoPost,bundle)
        }
    }

    fun setData(post: List<TkPostData>){
        this.postList = post
        notifyDataSetChanged()
    }
}