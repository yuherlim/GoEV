package com.example.goev.database

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChargingStationViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<ChargingStation>>
    private val repository: ChargingStationRepository

    init {
        val chargingStationDao = ChargingStationDatabase.getDatabase(application).chargingStationDao()
        repository = ChargingStationRepository(chargingStationDao)
        readAllData = repository.readAllData
    }

    fun addChargingStation(chargingStation: ChargingStation){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addChargingStation(chargingStation)
        }
    }

    fun updateChargingStation(chargingStation: ChargingStation) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateChargingStation(chargingStation)
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

    fun updateChargingStationImage(uploadedImage: ByteArray) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateChargingStationImage(uploadedImage)
        }
    }

}