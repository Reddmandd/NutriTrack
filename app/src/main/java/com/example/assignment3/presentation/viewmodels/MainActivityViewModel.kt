package com.example.assignment3.presentation.viewmodels

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assignment3.presentation.activities.MainActivity

import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import com.example.assignment3.data.model.AuthManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    fun firstLaunch(context: Context, patientViewModel: PatientViewModel) {
        val sharedPref = context.getSharedPreferences("data", MODE_PRIVATE)

        if (!sharedPref.contains("launched")) {
            sharedPref.edit() {
                putBoolean("launched", true)
                apply()
            }
            CoroutineScope(Dispatchers.IO).launch {
                patientViewModel.parseCsvData(context)
            }
        }


        val currentUserId = sharedPref.getInt("current_user", -1)
        if(currentUserId != -1){
            AuthManager.login(currentUserId, context)
        }
    }

    fun checkLoggedIn(): Boolean{
        if(AuthManager.getPatientId() != null){
            return true
        }
        return false
    }
}