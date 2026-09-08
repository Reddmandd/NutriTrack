package com.example.assignment3.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.assignment3.data.model.UsersDatabase
import com.example.assignment3.data.model.FoodIntake
import com.example.assignment3.data.model.Patient
import kotlinx.coroutines.flow.Flow

class FoodIntakeRepository(private val context: Context) {

    private val foodIntakeDao = UsersDatabase.Companion.getDatabase(context).foodIntakeDao()

    suspend fun insertFoodIntake(foodIntake: FoodIntake) {
        foodIntakeDao.insert(foodIntake)
    }

    suspend fun getFoodIntakeByPatientId(patientId: Int): FoodIntake? {
        return foodIntakeDao.getFoodIntakeByPatientId(patientId)
    }

    suspend fun updateFoodIntake(foodIntake: FoodIntake) {
        foodIntakeDao.updateFoodIntake(foodIntake)
    }

    fun getAllFoodIntake(): LiveData<List<FoodIntake>> = foodIntakeDao.getAll()


}