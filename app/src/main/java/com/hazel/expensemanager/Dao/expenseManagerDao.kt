package com.hazel.expensemanager.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hazel.expensemanager.Entities.ExpenseManager
import com.hazel.expensemanager.Entities.User

@Dao
interface ExpenseManagerDao {
    @Insert
    suspend fun insert(expense: ExpenseManager)

    @Query("SELECT * FROM expenseManager")
    fun getAllExpenses(): LiveData<List<ExpenseManager>>

    @Query("SELECT * FROM expenseManager where status = 'Expense'")
    fun getExpenses(): LiveData<List<ExpenseManager>>

    @Query("SELECT * FROM expenseManager where status = 'Income'")
    fun getIncomeExpenses(): LiveData<List<ExpenseManager>>

    @Query("SELECT * FROM expenseManager WHERE date >= :startTime")
    fun getResultsForLastNDays(startTime: Long): LiveData<List<ExpenseManager>>

}