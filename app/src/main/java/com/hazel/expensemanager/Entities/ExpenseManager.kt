package com.hazel.expensemanager.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenseManager")
data class ExpenseManager(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Long = 0,
    val status:String,
    val title: String,
    val description: String,
    val amount: String,
    val day:String,
    val month:String,
    val year: String,
    val date: Long
)
