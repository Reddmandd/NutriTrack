package com.example.assignment3.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.assignment3.data.model.FoodIntake
import com.example.assignment3.data.model.Patient
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodIntakeDao {

    @Insert
    suspend fun insert(foodIntake: FoodIntake)

    @Query("SELECT * FROM food_intakes WHERE patientId = :patientId")
    suspend fun getFoodIntakeByPatientId(patientId: Int): FoodIntake?

    @Update
    suspend fun updateFoodIntake(foodIntake: FoodIntake)

    @Query("SELECT * FROM food_intakes")
    fun getAll(): LiveData<List<FoodIntake>>
}