package com.example.goev.database

import androidx.lifecycle.LiveData

class ChargingStationRepository(private val chargingStationDao: ChargingStationDao) {

    val readAllData: LiveData<List<ChargingStation>> = chargingStationDao.readAllData()

    suspend fun addChargingStation(chargingStation: ChargingStation){
        chargingStationDao.addChargingStation(chargingStation)
    }

}