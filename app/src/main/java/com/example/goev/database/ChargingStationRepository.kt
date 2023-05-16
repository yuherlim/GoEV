package com.example.goev.database

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class ChargingStationRepository(private val chargingStationDao: ChargingStationDao) {

    val readAllData: LiveData<List<ChargingStation>> = chargingStationDao.readAllData()

    suspend fun addChargingStation(chargingStation: ChargingStation){
        chargingStationDao.addChargingStation(chargingStation)
    }

    suspend fun updateChargingStation(chargingStation: ChargingStation) {
        chargingStationDao.updateChargingStation(chargingStation)
    }

    suspend fun deleteChargingStation(chargingStation: ChargingStation) {
        chargingStationDao.deleteChargingStation(chargingStation)
    }

    suspend fun deleteAllChargingStations() {
        chargingStationDao.deleteAllChargingStations()
    }

    fun searchDatabase(searchQuery: String): Flow<List<ChargingStation>> {
        return chargingStationDao.searchDatabase(searchQuery)
    }

    fun updateChargingStationImage(uploadedImage: ByteArray, id: Int) {
        chargingStationDao.updateChargingStationImage(uploadedImage, id)
    }

//    fun getChargingStation(id: Int) : ChargingStation? {
//        return chargingStationDao.getChargingStation(id)
//    }

}