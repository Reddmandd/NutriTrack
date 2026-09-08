package com.example.assignment3.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.assignment3.data.model.AveragePatient
import com.example.assignment3.data.model.UsersDatabase
import com.example.assignment3.data.model.Patient
import kotlinx.coroutines.flow.Flow
import java.io.BufferedReader
import java.io.InputStreamReader

class PatientRepository(context: Context) {

    private val patientDao = UsersDatabase.Companion.getDatabase(context).patientDao()

    suspend fun insertPatient(patient: Patient) {
        patientDao.insert(patient)
    }

    suspend fun getPatientById(patientId: Int): Patient {
        return patientDao.getPatientById(patientId)
    }

    suspend fun updatePatient(patient: Patient){
        patientDao.updatePatient(patient)
    }

    fun getAllPatients(): LiveData<List<Patient>> {
        return patientDao.getAll()}

    suspend fun getPatientsAverageScore(patientSex: String) : AveragePatient{
        return patientDao.getPatientsAverageScore(patientSex)
    }

    suspend fun parseCsvToDatabase(context: Context){
        val assets = context.assets
        val inputStream = assets.open("user_info.csv")
        val reader  = BufferedReader(InputStreamReader(inputStream))

        var patients = mutableListOf<Patient>()
        reader.useLines { lines ->
            lines.drop(1).forEach { line ->
                val info = line.split(",")
                val id = info[1].toInt()
                val phone_number = info[0]
                val gender = info[2]

                var fruitServing = info[21].toFloat()
                var fruitVariety = info[22].toFloat()

                val total_score: Float
                val discretionary_foods: Float
                val vegetables: Float
                val fruit: Float
                val grains: Float
                val whole_grains: Float
                val meat: Float
                val dairy: Float
                val sodium: Float
                val alcohol: Float
                val water: Float
                val sugar: Float
                val saturated_fat: Float
                val unsaturated_fat: Float

                if(gender == "Male"){
                    total_score = info[3].toFloat()
                    discretionary_foods = info[5].toFloat()
                    vegetables = info[8].toFloat()
                    fruit = info[19].toFloat()
                    grains = info[29].toFloat()
                    whole_grains = info[35].toFloat()
                    meat = info[36].toFloat()
                    dairy = info[40].toFloat()
                    sodium = info[43].toFloat()
                    alcohol = info[46].toFloat()
                    water = info[49].toFloat()
                    sugar = info[54].toFloat()
                    saturated_fat = info[57].toFloat()
                    unsaturated_fat = info[60].toFloat()
                }

                else{
                    total_score = info[4].toFloat()
                    discretionary_foods = info[6].toFloat()
                    vegetables = info[9].toFloat()
                    fruit = info[20].toFloat()
                    grains = info[30].toFloat()
                    whole_grains = info[36].toFloat()
                    meat = info[37].toFloat()
                    dairy = info[41].toFloat()
                    sodium = info[44].toFloat()
                    alcohol = info[47].toFloat()
                    water = info[50].toFloat()
                    sugar = info[55].toFloat()
                    saturated_fat = info[58].toFloat()
                    unsaturated_fat = info[61].toFloat()
                }
            patients.add(
                Patient(
                    patientId = id,
                    patientPhone = phone_number,
                    patientSex = gender,
                    totalScore = total_score,
                    discretionaryFoodScore = discretionary_foods,
                    vegetableScore = vegetables,
                    fruitScore = fruit,
                    grainScore = grains,
                    wholeGrainScore = whole_grains,
                    meatScore = meat,
                    dairyScore = dairy,
                    sodiumScore = sodium,
                    alcoholScore = alcohol,
                    waterScore = water,
                    sugarScore = sugar,
                    unsaturatedFatScore = unsaturated_fat,
                    saturatedFatScore = saturated_fat,
                    fruitServing = fruitServing,
                    fruitVariety = fruitVariety
                )
            )

            }
        }

    patientDao.insertAll(patients)
    }
}