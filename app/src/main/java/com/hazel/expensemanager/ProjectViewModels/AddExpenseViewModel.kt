package com.hazel.expensemanager.ProjectViewModels

import androidx.lifecycle.ViewModel
import java.util.*

class AddExpenseViewModel():ViewModel() {
    private val calendar = Calendar.getInstance()

    fun getSelectedDate(): String {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        return "$dayOfMonth-$month-$year"
    }

    fun updateSelectedDate(year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
    }

}