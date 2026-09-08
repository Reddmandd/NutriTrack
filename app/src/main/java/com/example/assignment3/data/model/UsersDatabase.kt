package com.example.assignment3.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.assignment3.data.dao.FoodIntakeDao
import com.example.assignment3.data.dao.MessageDao
import com.example.assignment3.data.dao.PatientDao

@Database(entities = [Patient::class, FoodIntake::class, MotivationalMessage::class], version = 1, exportSchema = false)

    abstract class UsersDatabase : RoomDatabase() {

    abstract fun patientDao(): PatientDao

    abstract fun messageDao(): MessageDao

    abstract fun foodIntakeDao(): FoodIntakeDao


    companion object {
        // Singleton instance of the database
        @Volatile
        private var Instance: UsersDatabase? = null

        /**
         * Retrieves the singleton instance of the database.
         * If an instance already exists, it returns the existing
         * instance. Otherwise, it creates a new instance of the database.
         * @param context The context of the application.
         * @return The singleton instance of CollegeDatabase.
         */
        fun getDatabase(context: Context): UsersDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, UsersDatabase::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }


    }
}