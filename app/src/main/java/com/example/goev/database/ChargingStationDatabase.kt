package com.example.goev.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ChargingStation::class], version = 2, exportSchema = false)
abstract class ChargingStationDatabase : RoomDatabase() {

    abstract fun chargingStationDao(): ChargingStationDao

    companion object {
        @Volatile
        private var INSTANCE: ChargingStationDatabase? = null

        fun getDatabase(context: Context): ChargingStationDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChargingStationDatabase::class.java,
                    "charging_station_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }

}