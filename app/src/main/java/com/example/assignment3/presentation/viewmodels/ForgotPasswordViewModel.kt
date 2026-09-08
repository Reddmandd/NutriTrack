package com.example.assignment3.presentation.viewmodels

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.assignment3.data.model.AuthManager
import com.example.assignment3.data.model.Patient
import com.example.assignment3.presentation.viewmodels.ui.theme.Assignment3Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ForgotPasswordViewModel(application: Application) : AndroidViewModel(application) {

    var isLoggedIn by mutableStateOf(false)

    var password by mutableStateOf("")

    var name by mutableStateOf("")

    var selected_id by mutableStateOf("")

    var is_expanded by mutableStateOf(false)

    var phone_number by mutableStateOf("")

    var confirm_password by mutableStateOf("")

    private val _toast = MutableLiveData<String>("")
    val toast: LiveData<String> = _toast

    fun changePassword(patientViewModel: PatientViewModel){
        CoroutineScope(Dispatchers.IO).launch {
            if ((password == "") or (confirm_password == "") or (selected_id == "")) {
                _toast.postValue("ID/Password Field is Empty")
            } else if (phone_number == "") {
                _toast.postValue("Phone number field is empty")
            } else if (name == ""){
                _toast.postValue("Name field is empty")
            }

            else {
                val patient: Patient
                if (isLoggedIn) {
                    patient = patientViewModel.getPatientById(AuthManager.getPatientId() ?: -1)
                } else {
                    patient = patientViewModel.getPatientById(selected_id.toInt())
                }

                if (!isLoggedIn && patient.patientPasswordHash == null) {
                    _toast.postValue("Patient has not registered before")
                } else if (!isLoggedIn && name != patient.name) {
                    _toast.postValue("Name entered is incorrect")
                }else if (!isLoggedIn && phone_number != patient.patientPhone) {
                    _toast.postValue("Phone number entered is incorrect")
                } else if (password.length < 8) {
                    _toast.postValue("Password must be at least 8 characters long!")

                } else if (confirm_password != password) {
                    _toast.postValue("Passwords must match!")

                } else if (patient.patientPasswordHash == patientViewModel.hashString(password)) {
                    _toast.postValue("Password should not be the same as the last password")
                } else {
                    patient.patientPasswordHash = patientViewModel.hashString(password)
                    patientViewModel.update(patient)
                    _toast.postValue("Password change success, ${patient.name}!")
                }
            }
        }
    }

    fun checkIsLoggedIn(navController: NavController?){
        isLoggedIn = navController != null
    }

    fun goToNextScreen(context: Context, text: String, navController: NavController?){
        password = ""
        confirm_password = ""
        if(navController == null) {
            val activity = context as? Activity
            activity?.finish()
        }
        else{
            navController.navigate("settings")
        }
    }
    fun hasToast(): Boolean{
        return _toast.value != ""
    }

    fun clearToast(){
        _toast.value = ""
    }
}

