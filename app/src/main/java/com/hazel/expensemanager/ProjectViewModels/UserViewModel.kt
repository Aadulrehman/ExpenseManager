package com.hazel.expensemanager.ProjectViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hazel.expensemanager.Entities.User
import com.hazel.expensemanager.Repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository):ViewModel() {

    fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO){
            userRepository.insert(user)
        }
    }
    suspend fun findByEmail(email:String):String?{
        return userRepository.findByEmail(email)
    }
    suspend fun matchPassToValidate(email: String): String?{
        return userRepository.matchPassToValidate(email)
    }

}