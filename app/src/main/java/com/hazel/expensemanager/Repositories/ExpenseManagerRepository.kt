package com.hazel.expensemanager.Repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.hazel.expensemanager.Dao.ExpenseManagerDao
import com.hazel.expensemanager.Entities.ExpenseManager

class ExpenseManagerRepository(private val expenseManagerDao: ExpenseManagerDao) {
    val allExpenses: LiveData<List<ExpenseManager>> = expenseManagerDao.getAllExpenses()
    val allExpenseList: LiveData<List<ExpenseManager>> = expenseManagerDao.getExpenses()
    val allIncomeList: LiveData<List<ExpenseManager>> = expenseManagerDao.getIncomeExpenses()

    suspend fun insert(expense: ExpenseManager) {
        expenseManagerDao.insert(expense)
    }
    fun getExpenses(): LiveData<Double> {
        return expenseManagerDao.getAllExpenses().map { expenses ->
            expenses.filter { it.status == "Expense" }
                .sumOf { it.amount.toDouble() }
        }
    }

    fun getIncome(): LiveData<Double> {
        return expenseManagerDao.getAllExpenses().map { expenses ->
            expenses.filter { it.status == "Income" }
                .sumOf { it.amount.toDouble() }
        }
    }

    fun getResultsForLastNDays(startTime: Long): LiveData<List<ExpenseManager>> {
        return expenseManagerDao.getResultsForLastNDays(startTime)
    }


}