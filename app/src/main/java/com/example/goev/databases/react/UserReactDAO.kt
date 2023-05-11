package com.example.goev.databases.react

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.goev.databases.TipsAndKnowledgeDatabase

@Dao
interface UserReactDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserReaction(userReactData: UserReactData)

    @Update
    fun updateUserReaction(userReactData: UserReactData)

    @Query("DELETE FROM reacts WHERE postID = :postId AND userID = :userId")
    fun deleteUserReaction(userId:Long,postId: Long)

    @Query("SELECT * FROM reacts WHERE postID = :postID")
    fun getAllReactsOfAPost(postID: Long): LiveData<List<UserReactData>>

    @Query("SELECT * FROM reacts WHERE userID = :userId AND postID = :postId")
    fun getAUserReactOnAPost(userId: Long, postId: Long): UserReactData?

    @Query("UPDATE reacts SET reaction = :react WHERE userID = :userID AND postID = :postID")
    suspend fun updateLikeDislikeCount(userID: Long, postID: Long, react: Int)

//    @Query("UPDATE posts SET likes = likes + 1 WHERE postID = :postId")
//    suspend fun incrementLikes(postId: Long)
//
//    @Query("UPDATE posts SET dislikes = dislikes + 1 WHERE postID = :postId")
//    suspend fun incrementDislikes(postId: Long)
//
//    @Query("UPDATE posts SET likes = likes - 1 WHERE postID = :postId")
//    suspend fun decrementLikes(postId: Long)
//
//    @Query("UPDATE posts SET dislikes = dislikes - 1 WHERE postID = :postId")
//    suspend fun decrementDislikes(postId: Long)



//    @Transaction
//    suspend fun updateUserReaction(userId: Long, postId: Long, reaction: Int) {
//        // Check if user has already reacted to this post
//        val existingReact = getAUserReactOnAPost(userId, postId)
//        if (existingReact == null) {
//            // User has not reacted to this post before, create a new UserReactData object
//            val newReact = UserReactData(userId, postId, reaction)
//            insertUserReaction(newReact)
//            // Increment totalLikes or totalDislikes in posts table based on reaction value
//            if (reaction == 1) {
//                incrementLikes(postId)
//            } else if (reaction == 0) {
//                incrementDislikes(postId)
//            }
//        } else {
//            // User has already reacted to this post, update the existing UserReactData object
//            existingReact.reaction = reaction
//            updateReact(existingReact)
//            // Decrement totalLikes or totalDislikes in posts table based on previous reaction value
//            if (existingReact.reaction == 1) {
//                decrementLikes(postId)
//            } else if (existingReact.reaction == 0) {
//                decrementDislikes(postId)
//            }
//            // Increment totalLikes or totalDislikes in posts table based on new reaction value
//            if (reaction == 1) {
//                incrementLikes(postId)
//            } else if (reaction == 0) {
//                incrementDislikes(postId)
//            }
//        }
//    }
}