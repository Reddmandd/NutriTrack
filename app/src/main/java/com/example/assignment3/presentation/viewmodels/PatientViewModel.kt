package com.example.assignment3.presentation.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assignment3.data.model.AveragePatient
import com.example.assignment3.data.model.Patient
import com.example.assignment3.data.repositories.PatientRepository
import java.security.MessageDigest

class PatientViewModel(application: Application) : AndroidViewModel(application) {

    val repository = PatientRepository(context = application.applicationContext)

    private val _allPatients = MutableLiveData<List<Patient>>()
    val allPatients: LiveData<List<Patient>> get() = _allPatients

    fun loadPatients() {
        repository.getAllPatients().observeForever { patients ->
            _allPatients.value = patients
        }
    }

    suspend fun insert(patient: Patient) = repository.insertPatient(patient)

    suspend fun getPatientById(patientId: Int): Patient {
        return repository.getPatientById(patientId)
    }

    suspend fun update(patient: Patient){
        repository.updatePatient(patient)
    }


    suspend fun registerPatient(patientId: Int, password: String, name: String){
        val patient: Patient = repository.getPatientById(patientId)
        patient.patientPasswordHash = hashString(password)
        patient.name = name
        repository.updatePatient(patient)
        return
    }
    suspend fun parseCsvData(context: Context){
        repository.parseCsvToDatabase(context)
    }

    suspend fun getPatientsAverageScores(patientSex: String): AveragePatient{
       return repository.getPatientsAverageScore(patientSex)
    }


    // This is a function taken from here: https://gist.github.com/lovubuntu/164b6b9021f5ba54cefc67f60f7a1a25
    // Author is reastland, altered slightly
   fun hashString(input: String): String {
        return MessageDigest
            .getInstance("SHA-256")
            .digest(input.toByteArray())
            .fold("", { str, it -> str + "%02x".format(it) })
    }
}