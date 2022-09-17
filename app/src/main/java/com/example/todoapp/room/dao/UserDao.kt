package com.example.todoapp.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todoapp.model.UserModel
@Dao
interface UserDao {
@Insert
fun userInsert(userModel: ArrayList<UserModel>)

@Query("SELECT * FROM UserData")
fun userData():List<UserModel>
}