package com.example.goev.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.goev.database.user.UserData
import com.example.goev.database.user.UserDatabase
import com.example.goev.database.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsPrivacyAccountViewModel (application: Application) : AndroidViewModel(application){

    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getInstance(application).userDao()
        repository = UserRepository(userDao)
    }

    fun updateUserName(newUserName : String){
        viewModelScope.launch (Dispatchers.IO) {
            repository.updateUserName(newUserName)
        }
    }

    fun updateUserEmail(newUserEmail : String){
        viewModelScope.launch (Dispatchers.IO) {
            repository.updateUserEmail(newUserEmail)
        }
    }

    fun updateUserPassword(newUserPassword : String){
        viewModelScope.launch (Dispatchers.IO) {
            repository.updateUserPassword(newUserPassword)
        }
    }

    fun validatePassword(password1 : String, password2 : String): Boolean{
        return password1 == password2
    }

    fun getLoggedInUser(callback: (UserData?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getLoggedInUser()
            callback(result)
        }
    }

    fun validatePreviousPassword(previousPass : String, previousPass2 : String):Boolean{
        return previousPass == previousPass2
    }

    fun isUserNameExists(userName: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.isUserNameExists(userName)
            callback(result)
        }
    }

    fun isUserEmailExists(email: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.isUserEmailExists(email)
            callback(result)
        }
    }

    fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z\\d._-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}")
        return email.matches(emailRegex)
    }

}