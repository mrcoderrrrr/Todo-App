package com.example.todoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "UserData")
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val email:String,
    val age:String,
    val dob:String
)
