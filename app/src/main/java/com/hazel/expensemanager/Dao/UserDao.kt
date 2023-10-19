package com.hazel.expensemanager.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hazel.expensemanager.Entities.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT email FROM users WHERE email = :email")
    suspend fun findByEmail(email: String): String?

    @Query("SELECT password FROM users WHERE email = :email")
    suspend fun matchPassToValidate(email: String): String?


}