package com.example.goev.databaseChargingStation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChargingStationViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<ChargingStation>>
    private val repository: ChargingStationRepository

    init {
        val chargingStationDao =
            ChargingStationDatabase.getDatabase(application).chargingStationDao()
        repository = ChargingStationRepository(chargingStationDao)
        readAllData = repository.readAllData
    }

    fun addChargingStation(chargingStation: ChargingStation) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addChargingStation(chargingStation)
        }
    }

    fun updateChargingStation(chargingStation: ChargingStation) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateChargingStation(chargingStation)
        }
    }

    fun updateChargingStationImage(uploadedImage: ByteArray, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateChargingStationImage(uploadedImage, id)
        }
    }

    fun deleteChargingStation(chargingStation: ChargingStation) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteChargingStation(chargingStation)
        }
    }

    fun deleteAllChargingStations() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllChargingStations()
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ChargingStation>> {
        return repository.searchDatabase(searchQuery).asLiveData()
    }

    fun getChargingStationById(id: Int, callback: (ChargingStation?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getChargingStationById(id)
            callback(result)
        }
    }

}