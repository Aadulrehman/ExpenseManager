package com.hazel.expensemanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository):ViewModel() {

    fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO){
            userRepository.insert(user)
        }
    }

    suspend fun findByEmail(email:String):String?{
        return userRepository.findByEmail(email)
    }


}