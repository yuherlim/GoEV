package com.example.goev.userProfile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.goev.database.user.UserData
import com.example.goev.database.user.UserDatabase
import com.example.goev.database.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getInstance(application).userDao()
        repository = UserRepository(userDao)
    }

    fun updateUserProfileInfo(
        userId: Int,
        newProfileName: String,
        newPhoneNumber: String,
        newEmail: String,
        newGender: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUserProfileInfo(
                userId,
                newProfileName,
                newPhoneNumber,
                newEmail,
                newGender
            )
        }
    }

    fun updateUser(userData: UserData){
        repository.updateUser(userData)
    }

    fun getLoggedInUser(): UserData? {
        return repository.getLoggedInUser()
    }
}