package com.example.assignment3.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.assignment3.data.model.AuthManager
import com.example.assignment3.data.model.Patient
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ChangeNameViewModel(application: Application) : AndroidViewModel(application) {
    var newName by mutableStateOf("")

    private val _toast = MutableLiveData<String>("")
    val toast: LiveData<String> = _toast


    fun registerPatient(patientViewModel: PatientViewModel){
        runBlocking {
            var patient = patientViewModel.getPatientById(AuthManager.getPatientId() ?: -1)

            if (newName == "") {
                _toast.postValue("Name field is empty!")
            }
            else if (patient.name == newName) {
                _toast.postValue("New name cannot be the same as the old one!")
            }
            else {
                patient.name = newName
                patientViewModel.update(patient)
                _toast.postValue("Name Change Success!")
            }
        }
    }
    fun finishChangeName(){
        newName = ""
    }

    fun checkToast(): Boolean{
        return _toast.value != ""
    }

    fun clearToast(){
        return _toast.postValue("")
    }

}