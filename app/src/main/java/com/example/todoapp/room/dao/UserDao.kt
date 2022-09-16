package com.example.todoapp.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todoapp.model.UserModel

@Dao
interface UserDao {
@Insert
fun userInsert(userModel: UserModel)

@Query("SELECT * FROM userData")
fun userData():ArrayList<UserModel>
}