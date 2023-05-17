package com.example.goev.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.goev.database.forum.*
import com.example.goev.database.user.UserDao
import com.example.goev.database.user.UserData
import com.example.goev.databaseChargingStation.ChargingStation
import com.example.goev.databaseChargingStation.ChargingStationDao
import com.example.goev.databases.post.TkPostDAO
import com.example.goev.databases.post.TkPostData
import com.example.goev.databases.postcomment.TkPostCommentDAO
import com.example.goev.databases.postcomment.TkPostCommentData
import com.example.goev.databases.react.UserReactDAO
import com.example.goev.databases.react.UserReactData

//
@Database(
    entities = [UserData::class, TkPostData::class, TkPostCommentData::class, UserReactData::class, ForumPostData::class,
        ForumPostLikeData::class,
        ForumPostDislikeData::class,
        ForumCommentData::class,
        ForumCommentLikeData::class,
        ForumCommentDislikeData::class,
        ForumRepliesData::class,
        ChargingStation::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
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
    abstract val forumRepliesDao: ForumRepliesDao
    abstract val chargingStationDao: ChargingStationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "TipsAndKnowledgeDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}