package com.example.goev

import android.graphics.ColorSpace
import android.icu.text.Transliterator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ktAdapter(private val data: List<tkPost>):RecyclerView.Adapter<ktAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tips_and_knowledge_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ktAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.title)
        private val thumbnail: ImageView = itemView.findViewById(R.id.postThumbnail)
        private val contentPrevTextView: TextView = itemView.findViewById(R.id.contentPreview)
        private val totalLikesTV: TextView = itemView.findViewById(R.id.totalLikes)
        private val totalDislikesTV: TextView = itemView.findViewById(R.id.totalDislikes)
        private val totalCommentsTV: TextView = itemView.findViewById(R.id.totalComments)
        private val likeButton:ImageView = itemView.findViewById(R.id.likeButton)
//        private val dislikeButton: ImageView = itemView.findViewById(R.id.totalComments)
//        private val comment: ImageView = itemView.findViewById(R.id.totalComments)
//        private val share: ImageView = itemView.findViewById(R.id.shareButtons)

        fun bind(post: tkPost) {
            titleTextView.text = post.postTitle
            post.thumbnailID = R.drawable.test
            thumbnail.setImageResource(post.thumbnailID)
            contentPrevTextView.text = post.contentPreview
            totalLikesTV.text = post.totalLikes.toString()
            totalDislikesTV.text = post.totalDislikes.toString()
            totalCommentsTV.text = post.totalComments.toString()
        }

    }
}
