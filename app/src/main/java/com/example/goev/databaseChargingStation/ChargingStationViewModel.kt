package com.example.goev.databaseChargingStation

import android.app.Application
import androidx.lifecycle.*
import com.example.goev.database.user.UserData
import com.example.goev.database.user.UserRepository
import com.example.goev.databases.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChargingStationViewModel(application: Application) : AndroidViewModel(application) {

    //    val readAllData: LiveData<List<ChargingStation>>
    private val chargingStationRepository: ChargingStationRepository
    private val userRepository: UserRepository

    private lateinit var currentLoginUser: UserData

    private val _currentChargingStationList = MutableLiveData<List<ChargingStation>?>()
    val currentChargingStationList: LiveData<List<ChargingStation>?>
        get() = _currentChargingStationList

    private val _currentLoginUserId = MutableLiveData<Int>()
    val currentLoginUserId: LiveData<Int>
        get() = _currentLoginUserId

    init {
        val chargingStationDao = AppDatabase.getInstance(application).chargingStationDao
        val userDao = AppDatabase.getInstance(application).userDAO
        chargingStationRepository = ChargingStationRepository(chargingStationDao)
        userRepository = UserRepository(userDao)
//        readAllData = repository.readAllData
    }

    fun loadChargingStations() {
        viewModelScope.launch(Dispatchers.IO) {
            setCurrentLoginUser()
            val userId = currentLoginUser.id
            val chargingStationList = chargingStationRepository.getAllChargingStations()
            withContext(Dispatchers.Main) {
                _currentChargingStationList.postValue(chargingStationList?.filter { it.userId == userId })
            }
        }
    }

    fun addChargingStation(chargingStation: ChargingStation) {
        viewModelScope.launch(Dispatchers.IO) {
            chargingStationRepository.addChargingStation(chargingStation)
        }
    }

    fun updateChargingStation(chargingStation: ChargingStation) {
        viewModelScope.launch(Dispatchers.IO) {
            chargingStationRepository.updateChargingStation(chargingStation)
        }
    }

    fun updateChargingStationImage(uploadedImage: ByteArray, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            chargingStationRepository.updateChargingStationImage(uploadedImage, id)
        }
    }

    fun deleteChargingStation(chargingStation: ChargingStation) {
        viewModelScope.launch(Dispatchers.IO) {
            chargingStationRepository.deleteChargingStation(chargingStation)
        }
    }

    fun deleteAllChargingStations(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            chargingStationRepository.deleteAllChargingStations(userId)
        }
        // empty the list to reflect ui changes
        _currentChargingStationList.value = emptyList()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ChargingStation>> {
        return chargingStationRepository.searchDatabase(searchQuery).asLiveData()
    }

    fun getChargingStationById(id: Int, callback: (ChargingStation?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = chargingStationRepository.getChargingStationById(id)
            callback(result)
        }
    }

    fun getCurrentLoginUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentLoggedInUser = userRepository.getLoggedInUser()
            // Update the live data value for user id
            withContext(Dispatchers.Main) {
                _currentLoginUserId.postValue(currentLoggedInUser!!.id)
            }
        }
    }

    private fun setCurrentLoginUser() {
        val currentLoggedInUser = userRepository.getLoggedInUser()
        if (currentLoggedInUser != null) {
            currentLoginUser = currentLoggedInUser
        }
    }


}