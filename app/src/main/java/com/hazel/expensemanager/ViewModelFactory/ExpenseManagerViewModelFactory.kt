package com.hazel.expensemanager.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hazel.expensemanager.Repositories.ExpenseManagerRepository
import com.hazel.expensemanager.ProjectViewModels.ExpenseManagerViewModel

class ExpenseManagerViewModelFactory(private val expenseManagerRepository: ExpenseManagerRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ExpenseManagerViewModel(expenseManagerRepository) as T
    }
}