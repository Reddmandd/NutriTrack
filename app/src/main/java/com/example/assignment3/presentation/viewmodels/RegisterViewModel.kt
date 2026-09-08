package com.example.assignment3.presentation.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    var password by mutableStateOf("")
    var selected_id by mutableStateOf("")
    var is_expanded by mutableStateOf(false)
    var confirm_password by mutableStateOf("")
    var name by mutableStateOf("")

    private val _toast = MutableLiveData<String>("")
    val toast: LiveData<String> = _toast



    fun registerPatient(patientViewModel: PatientViewModel){
        viewModelScope.launch {

            var patient = patientViewModel.getPatientById(selected_id.toInt())

            if ((selected_id == "") or (password == "") or (confirm_password == "")) {
                _toast.postValue("ID/Password field is empty")
            }
            else if (patient.patientPasswordHash != null) {
                _toast.postValue("Patient has registered before!")
            }

            else if (name.isEmpty()) {
                _toast.postValue("Name field is empty!")

            } else if (password.length < 8) {
                _toast.postValue("Password must be at least 8 characters long!")

            } else if (confirm_password != password) {
                _toast.postValue("Passwords must match!")

            } else {
                patientViewModel.registerPatient(selected_id.toInt(), password, name)
                _toast.postValue("Registering Success, ${name}!")
            }
        }
    }

    fun checkToast(): Boolean{
        return _toast.value != ""
    }

    fun clearToast(){
        _toast.value = ""
    }

}

