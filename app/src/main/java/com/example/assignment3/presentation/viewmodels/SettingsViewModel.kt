package com.example.assignment3.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.assignment3.data.model.AuthManager
import com.example.assignment3.data.model.Patient
import com.example.assignment3.presentation.activities.Login
import kotlinx.coroutines.runBlocking

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    var name by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var patientId by mutableStateOf("")

    fun getPatient(patientViewModel: PatientViewModel){
        val patient: Patient
        runBlocking {
            patient = patientViewModel.getPatientById(AuthManager.getPatientId() ?: -1)
        }
        name = patient.name.toString()
        phoneNumber = patient.patientPhone
        patientId = patient.patientId.toString()
    }

    fun logout(context: Context){
        val intent = Intent(context, Login::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
        AuthManager.logout(context)
    }

}