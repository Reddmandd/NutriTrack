package com.example.assignment3.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
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
import com.example.assignment3.data.model.AuthManager
import com.example.assignment3.data.model.Patient
import com.example.assignment3.presentation.activities.Questionnaire
import com.example.assignment3.presentation.viewmodels.ui.theme.Assignment3Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _toast = MutableLiveData<String>("")
    val toast: LiveData<String> = _toast

    var password by mutableStateOf("")

    var is_logged_in by mutableStateOf(false)

    var selected_id by mutableStateOf("")

    var is_expanded by mutableStateOf(false)

    fun verifyPatient(patientViewModel: PatientViewModel, context: Context){
        val passwordHash = patientViewModel.hashString(password)
        var wantedPatient: Patient?= null


        if(selected_id == "") {
            _toast.postValue("No patient ID chosen!")
            return
        }

        else if(password == "") {
            _toast.postValue("Password Field is empty!")
            return
        }

        runBlocking {
            var aFlowWantedPatient: Patient = patientViewModel.getPatientById(selected_id.toInt())
            wantedPatient = aFlowWantedPatient
        }

        if (wantedPatient == null) {
            _toast.postValue("No patient ID chosen!")
        }
        else if (wantedPatient.patientPasswordHash != passwordHash) {
            _toast.postValue("Wrong password!")
        }
        else {
            AuthManager.login(selected_id.toInt(), context)
            _toast.postValue("Successfully logged in, ${wantedPatient.name}!")
        }
    }

    fun goToNextScreen(context: Context){
        if (is_logged_in){
            val intent = Intent(context, Questionnaire::class.java)
            context.startActivity(intent)
        }
    }

    fun hasToast(): Boolean{
        return _toast.value != ""
    }

    fun clearToast(){
        _toast.value = ""
    }
}

