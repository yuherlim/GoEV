package com.example.goev.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities=[TkPostData::class],version =1, exportSchema = false)
abstract class TkPostDatabase: RoomDatabase() {
    public abstract fun tkPostDAO(): TkPostDAO

    companion object {
        @Volatile
        private var INSTANCE: TkPostDatabase? = null

        fun getInstance(context: Context): TkPostDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TkPostDatabase::class.java,
                    "TkPostDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}