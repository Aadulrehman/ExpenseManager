package com.hazel.expensemanager.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hazel.expensemanager.Dao.ExpenseManagerDao
import com.hazel.expensemanager.Entities.User
import com.hazel.expensemanager.Dao.UserDao
import com.hazel.expensemanager.Entities.ExpenseManager

@Database(entities = [User::class, ExpenseManager::class], version = 4)
abstract class AppDatabase:RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun expenseManagerDao(): ExpenseManagerDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "expense_database"
                ).fallbackToDestructiveMigration().build()
            return instance!!
        }
    }
}