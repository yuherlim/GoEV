package com.example.goev.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ChargingStationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addChargingStation(chargingStation: ChargingStation)

    @Update
    suspend fun updateChargingStation(chargingStation:ChargingStation)

    @Delete
    suspend fun deleteChargingStation(chargingStation: ChargingStation)

    @Query("DELETE FROM charging_station_table")
    suspend fun deleteAllChargingStations()

    @Query("SELECT * FROM charging_station_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<ChargingStation>>

    @Query("SELECT * FROM charging_station_table WHERE name LIKE :searchQuery OR address LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<ChargingStation>>
}