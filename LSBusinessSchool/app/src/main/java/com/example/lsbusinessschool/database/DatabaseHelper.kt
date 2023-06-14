package com.example.lsbusinessschool.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lsbusinessschool.dao.AppDAO
import com.example.lsbusinessschool.model.CourseModel
import com.example.lsbusinessschool.model.StudentModel
import com.example.lsbusinessschool.model.UserModel

@Database(entities = [StudentModel::class, CourseModel::class, UserModel::class], version = 1)
abstract class DatabaseHelper: RoomDatabase() {

    abstract fun appDao(): AppDAO

    companion object{
        private lateinit var INSTANCE: DatabaseHelper

        fun getDatabase(context: Context): DatabaseHelper {
            if(!::INSTANCE.isInitialized) {
                synchronized(DatabaseHelper::class.java) {
                    INSTANCE = Room.databaseBuilder(context, DatabaseHelper::class.java, "studentDB")
                        //   .addMigrations(MIGRATION_1_2)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}