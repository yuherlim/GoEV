package com.example.goev.databaseChargingStation

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class ChargingStationRepository(private val chargingStationDao: ChargingStationDao) {

    val readAllData: LiveData<List<ChargingStation>> = chargingStationDao.readAllData()

    suspend fun addChargingStation(chargingStation: ChargingStation) {
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

    suspend fun updateChargingStationImage(uploadedImage: ByteArray, id: Int) {
        chargingStationDao.updateChargingStationImage(uploadedImage, id)
    }

    suspend fun getChargingStationById(id: Int): ChargingStation? {
        return chargingStationDao.getChargingStationById(id)
    }

}