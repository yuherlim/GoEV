package com.example.goev.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChargingStationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addChargingStation(chargingStation: ChargingStation)

    @Query("SELECT * FROM charging_station_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<ChargingStation>>

}