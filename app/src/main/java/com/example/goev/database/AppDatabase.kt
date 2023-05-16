package com.example.goev.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.goev.database.forum.*

@Database(entities =
[UserData::class,
     ForumPostData::class,
     ForumPostLikeData::class,
     ForumPostDislikeData::class,
     ForumCommentData::class,
     ForumCommentLikeData::class,
     ForumCommentDislikeData::class,
     ForumRepliesData::class,
     ForumHashtagData::class,
     ForumPostHashtagData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
     abstract val forumPostDao: ForumPostDao
     abstract val forumPostLikeDao: ForumPostLikeDao
     abstract val forumPostDislikeDao: ForumPostDislikeDao
     abstract val forumCommentDao: ForumCommentDao
     abstract val forumCommentLikeDao: ForumCommentLikeDao
     abstract val forumCommentDislikeDao: ForumCommentDislikeDao
     abstract val forumHashtagDao: ForumHashtagDao
     abstract val forumRepliesDao: ForumRepliesDao
     abstract val forumPostHashtagDao: ForumPostHashtagDao
     abstract val userDao: UserDao




     companion object {
          @Volatile
          private var INSTANCE: AppDatabase? = null
          fun getInstance(context: Context): AppDatabase {
               synchronized(this) {
                    var instance = INSTANCE
                    if (instance == null) {
                         instance = Room.databaseBuilder(
                              context.applicationContext,
                              AppDatabase::class.java,
                              "AppDatabase")
                              .fallbackToDestructiveMigration()
                              .build()
                    }
                    return instance
               }

          }
     }
}