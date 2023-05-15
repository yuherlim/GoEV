package com.example.goev.database

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChargingStationViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<ChargingStation>>
    private val repository: ChargingStationRepository

//    private var _isError = MutableLiveData<Boolean>()
//    val isError: LiveData<Boolean> = _isError
//    private var _onAdd = MutableLiveData<Boolean>()
//    val onAdd: LiveData<Boolean> = _onAdd

    init {
        val chargingStationDao = ChargingStationDatabase.getDatabase(application).chargingStationDao()
        repository = ChargingStationRepository(chargingStationDao)
        readAllData = repository.readAllData

        //for addStationFragment
//        _isError.value = false
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
//    fun setIsError(value: Boolean) {
//        _isError.value = value
//    }

//    fun setOnAdd(status: Boolean) {
//        _onAdd.value = status
//    }

}