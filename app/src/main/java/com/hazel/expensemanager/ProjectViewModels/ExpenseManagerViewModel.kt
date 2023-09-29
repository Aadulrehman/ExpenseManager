package com.hazel.expensemanager.ProjectViewModels

import androidx.lifecycle.*
import com.hazel.expensemanager.Entities.ExpenseManager
import com.hazel.expensemanager.Repositories.ExpenseManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ExpenseManagerViewModel(private val expenseManagerRepository: ExpenseManagerRepository):ViewModel() {
    val expenses: LiveData<Double> = expenseManagerRepository.getExpenses()
    val income: LiveData<Double> = expenseManagerRepository.getIncome()
    val allExpenses: LiveData<List<ExpenseManager>> = expenseManagerRepository.allExpenses
    val allExpenseList: LiveData<List<ExpenseManager>> = expenseManagerRepository.allExpenseList
    val allIncomeList: LiveData<List<ExpenseManager>> = expenseManagerRepository.allIncomeList
    private val selectedValueLiveData = MutableLiveData<Int>()

    fun setSelectedValue(value: Int) {
        selectedValueLiveData.value = value
    }
    fun getSelectedValue(): LiveData<Int> {
        return selectedValueLiveData
    }
    fun insertData(status:String,title: String,description: String,amount: String, day:String,month:String, year: String ){
        val selectedDate = Calendar.getInstance()
        selectedDate.set(year.toInt(), month.toInt(), day.toInt())
        val timestamp = selectedDate.timeInMillis

        viewModelScope.launch(Dispatchers.IO){
            val expense=ExpenseManager(0,status,title,description,amount,day,month,year,timestamp)
            expenseManagerRepository.insert(expense)
        }
    }
    fun getResultsForLastNDays(n: Int): LiveData<List<ExpenseManager>> {
        val startTime = getStartTimeForLastNDays(n)
        return expenseManagerRepository.getResultsForLastNDays(startTime)
    }
    private fun getStartTimeForLastNDays(n: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -n)
        return calendar.timeInMillis
    }
    fun calculateProfit(): LiveData<Double> {
        return MediatorLiveData<Double>().apply {
            addSource(expenses) { expenseValue ->
                val incomeValue = income.value ?: 0.0
                value = incomeValue - expenseValue
            }
            addSource(income) { incomeValue ->
                val expenseValue = expenses.value ?: 0.0
                value = incomeValue - expenseValue
            }
        }
    }
}