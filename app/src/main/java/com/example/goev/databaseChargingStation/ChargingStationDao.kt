package com.example.goev.databaseChargingStation

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ChargingStationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addChargingStation(chargingStation: ChargingStation)

    @Update
    suspend fun updateChargingStation(chargingStation: ChargingStation)

    @Delete
    suspend fun deleteChargingStation(chargingStation: ChargingStation)

    @Query("DELETE FROM charging_station_table where userId = :userId")
    suspend fun deleteAllChargingStations(userId: Int)

//    @Query("SELECT * FROM charging_station_table ORDER BY id ASC")
//    fun readAllData(): LiveData<List<ChargingStation>>

    @Query("SELECT * FROM charging_station_table ORDER BY id ASC")
    suspend fun getAllChargingStations(): List<ChargingStation>?

    @Query("SELECT * FROM charging_station_table WHERE userId = :userId AND (name LIKE :searchQuery OR address LIKE :searchQuery)")
    fun searchDatabase(userId: Int, searchQuery: String): Flow<List<ChargingStation>>

    @Query("UPDATE charging_station_table SET image = :uploadedImage WHERE id = :id")
    suspend fun updateChargingStationImage(uploadedImage: ByteArray, id: Int)

    @Query("SELECT * FROM charging_station_table WHERE id= :id")
    suspend fun getChargingStationById(id: Int): ChargingStation?

}