package com.example.goev.databaseChargingStation

import kotlinx.coroutines.flow.Flow

class ChargingStationRepository(private val chargingStationDao: ChargingStationDao) {

//    val readAllData: LiveData<List<ChargingStation>> = chargingStationDao.readAllData()

    suspend fun getAllChargingStations(): List<ChargingStation>? {
        return chargingStationDao.getAllChargingStations()
    }

    suspend fun addChargingStation(chargingStation: ChargingStation) {
        chargingStationDao.addChargingStation(chargingStation)
    }

    suspend fun updateChargingStation(chargingStation: ChargingStation) {
        chargingStationDao.updateChargingStation(chargingStation)
    }

    suspend fun deleteChargingStation(chargingStation: ChargingStation) {
        chargingStationDao.deleteChargingStation(chargingStation)
    }

    suspend fun deleteAllChargingStations(userId: Int) {
        chargingStationDao.deleteAllChargingStations(userId)
    }

    fun searchDatabase(userId: Int, searchQuery: String): Flow<List<ChargingStation>> {
        return chargingStationDao.searchDatabase(userId, searchQuery)
    }

    suspend fun updateChargingStationImage(uploadedImage: ByteArray, id: Int) {
        chargingStationDao.updateChargingStationImage(uploadedImage, id)
    }

    suspend fun getChargingStationById(id: Int): ChargingStation? {
        return chargingStationDao.getChargingStationById(id)
    }

}