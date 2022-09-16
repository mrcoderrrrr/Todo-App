package com.example.todoapp.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.model.UserModel
import com.example.todoapp.room.dao.UserDao

@Database(entities = [UserModel::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
abstract fun userDao():UserDao
    companion object {
        private var INSTANCE: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase? {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java, "userData.db"
                    ).allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}