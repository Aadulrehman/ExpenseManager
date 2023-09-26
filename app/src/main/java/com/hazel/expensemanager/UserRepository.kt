package com.hazel.expensemanager

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    suspend fun insert(user: User) {
        userDao.insert(user)
    }
    suspend fun findByEmail(email:String):String?{
        return userDao.findByEmail(email)
    }
}