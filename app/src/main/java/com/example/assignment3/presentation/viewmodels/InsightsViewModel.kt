package com.example.assignment3.presentation.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.assignment3.data.model.AuthManager
import com.example.assignment3.data.model.Patient
import kotlinx.coroutines.runBlocking

class InsightsViewModel(application: Application) : AndroidViewModel(application)  {
    var patient = mutableStateOf(Patient(patientId = 0, patientPhone = "-", patientSex = "-", name = "-"))

    fun getPatient(patientViewModel: PatientViewModel){
        runBlocking {
            patient.value = patientViewModel.getPatientById(AuthManager.getPatientId() ?: -1)
        }
    }
}

