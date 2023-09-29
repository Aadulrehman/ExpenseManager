package com.hazel.expensemanager.Repositories

import com.hazel.expensemanager.Dao.UserDao
import com.hazel.expensemanager.Entities.User

class UserRepository(private val userDao: UserDao) {
    suspend fun insert(user: User) {
        userDao.insert(user)
    }
    suspend fun findByEmail(email:String):String?{
        return userDao.findByEmail(email)
    }

    suspend fun matchPassToValidate(email: String): String?{
        return userDao.matchPassToValidate(email)
    }
}