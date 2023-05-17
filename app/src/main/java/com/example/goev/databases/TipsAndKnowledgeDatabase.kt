package com.example.goev.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.goev.TipsAndKnowledge
import com.example.goev.database.forum.*
import com.example.goev.databases.post.TkPostDAO
import com.example.goev.databases.post.TkPostData
import com.example.goev.databases.postcomment.TkPostCommentDAO
import com.example.goev.databases.postcomment.TkPostCommentData
import com.example.goev.databases.react.UserReactDAO
import com.example.goev.databases.react.UserReactData
import com.example.goev.database.user.UserData
import com.example.goev.database.user.UserDao

//
@Database(
    entities = [UserData::class, TkPostData::class, TkPostCommentData::class, UserReactData::class, ForumPostData::class,
        ForumPostLikeData::class,
        ForumPostDislikeData::class,
        ForumCommentData::class,
        ForumCommentLikeData::class,
        ForumCommentDislikeData::class,
        ForumRepliesData::class,
        ForumHashtagData::class,
        ForumPostHashtagData::class],
    version = 1,
    exportSchema = false
)
abstract class TipsAndKnowledgeDatabase : RoomDatabase() {
    abstract val userDAO: UserDao
    abstract val postCommentDAO: TkPostCommentDAO
    abstract val postDAO: TkPostDAO
    abstract val userReactDAO: UserReactDAO
    abstract val forumPostDao: ForumPostDao
    abstract val forumPostLikeDao: ForumPostLikeDao
    abstract val forumPostDislikeDao: ForumPostDislikeDao
    abstract val forumCommentDao: ForumCommentDao
    abstract val forumCommentLikeDao: ForumCommentLikeDao
    abstract val forumCommentDislikeDao: ForumCommentDislikeDao
    abstract val forumHashtagDao: ForumHashtagDao
    abstract val forumRepliesDao: ForumRepliesDao
    abstract val forumPostHashtagDao: ForumPostHashtagDao

    companion object {
        @Volatile
        private var INSTANCE: TipsAndKnowledgeDatabase? = null

        fun getInstance(context: Context): TipsAndKnowledgeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TipsAndKnowledgeDatabase::class.java,
                    "TipsAndKnowledgeDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}