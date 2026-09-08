package com.example.assignment3.presentation.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

class ClinicianLoginViewModel(application: Application) : AndroidViewModel(application) {
    var password by mutableStateOf("")

    fun checkPassword(): Pair<Boolean, String>{
        if(password.isEmpty()){
            return Pair(false, "Password field is empty!")
        }
        else if(password == "dollar-entry-apples"){
            password = ""
            return Pair(true, "Successfully logged in!")
        }
        else {
            return Pair(false, "Wrong password")
        }

    }


}