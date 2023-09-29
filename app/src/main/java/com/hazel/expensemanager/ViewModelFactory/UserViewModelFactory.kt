package com.hazel.expensemanager.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hazel.expensemanager.Repositories.UserRepository
import com.hazel.expensemanager.ProjectViewModels.UserViewModel

class UserViewModelFactory(private val userRepository: UserRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(userRepository) as T
    }
}