package com.example.assignment3.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assignment3.data.model.FoodIntake
import com.example.assignment3.data.model.Patient
import com.example.assignment3.data.repositories.FoodIntakeRepository
import kotlinx.coroutines.flow.Flow

class FoodIntakeViewModel(application: Application) : AndroidViewModel(application) {

    val repository = FoodIntakeRepository(context = application.applicationContext)

    private val _allFoodIntake = MutableLiveData<List<FoodIntake>>()
    val allFoodIntake: LiveData<List<FoodIntake>> get() = _allFoodIntake

    suspend fun insert(foodIntake: FoodIntake) = repository.insertFoodIntake(foodIntake)

    suspend fun getPatientById(patientId: Int): FoodIntake? {
        return repository.getFoodIntakeByPatientId(patientId)
    }

    suspend fun update(foodIntake: FoodIntake){
        repository.updateFoodIntake(foodIntake)
    }


}