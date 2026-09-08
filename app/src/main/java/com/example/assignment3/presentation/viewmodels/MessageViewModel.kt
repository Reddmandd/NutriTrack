package com.example.assignment3.presentation.viewmodels

import MessageRepository
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assignment3.data.model.AuthManager
import com.example.assignment3.data.model.MotivationalMessage
import com.example.assignment3.data.model.Patient
import com.example.assignment3.data.repositories.PatientRepository

class MessageViewModel(application: Application) : AndroidViewModel(application) {

    val repository = MessageRepository(context = application.applicationContext)

    suspend fun insertMessage(prompt : String){
        repository.insertMessages(MotivationalMessage(patientId = AuthManager.getPatientId() ?: -1, message = prompt))
    }

    fun getAllMessages(): LiveData<List<MotivationalMessage>>{
        return repository.getMessagesByPatientId(AuthManager.getPatientId() ?: -1)
    }

}