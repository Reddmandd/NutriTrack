package com.example.assignment3.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.assignment3.data.model.AveragePatient
import com.example.assignment3.data.model.Patient
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {

    @Insert
    suspend fun insert(patient: Patient)

    @Query("SELECT * FROM patients WHERE patientId = :patientId")
    suspend fun getPatientById(patientId: Int): Patient

    @Update
    suspend fun updatePatient(patient: Patient)

    @Query("SELECT * FROM patients")
    fun getAll(): LiveData<List<Patient>>

    @Insert
    suspend fun insertAll(patients: List<Patient>)

    @Query("SELECT patientSex, (totalScore) AS totalScore, AVG(vegetableScore) AS vegetableScore, AVG(fruitScore) AS fruitScore, AVG(grainScore)" +
            "AS grainScore, AVG(wholeGrainScore) AS wholeGrainScore, AVG(meatScore) AS meatScore, AVG(dairyScore) AS dairyScore," +
            "AVG(waterScore) AS waterScore, AVG(saturatedFatScore) AS saturatedFatScore, AVG(unsaturatedFatScore) AS unsaturatedFatScore, " +
            "AVG(sodiumScore) AS sodiumScore, AVG(sugarScore) AS sugarScore, AVG(alcoholScore) AS alcoholScore, AVG(discretionaryFoodScore) " +
            "AS discretionaryFoodScore FROM patients WHERE patientSex = :patientSex")
    suspend fun getPatientsAverageScore(patientSex: String): AveragePatient


}