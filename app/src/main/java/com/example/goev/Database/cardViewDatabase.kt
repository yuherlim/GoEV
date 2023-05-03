package com.example.goev.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.goev.Database.TipsAndKnowledgeCardView
import com.example.goev.Database.TipsAndKnowledgeCardViewDAO


@Database (entities = [TipsAndKnowledgeCardView::class], version=1, exportSchema = false)
abstract class cardViewDatabase:RoomDatabase(){
    abstract val sleepDatabaseDao: TipsAndKnowledgeCardViewDAO

    companion object {

        @Volatile
        private var INSTANCE: cardViewDatabase? = null

        fun getInstance(context: Context): cardViewDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        cardViewDatabase::class.java,
                        "sleep_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}