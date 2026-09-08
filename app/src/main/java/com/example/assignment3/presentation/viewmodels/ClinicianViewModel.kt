package com.example.assignment3.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.assignment3.BuildConfig
import com.example.assignment3.data.model.AuthManager
import com.example.assignment3.data.model.AveragePatient
import com.example.assignment3.data.model.Patient
import com.example.assignment3.data.model.UiState
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class ClinicianViewModel(application: Application) : AndroidViewModel(application) {
    private val _prompts = MutableLiveData<List<String>>(emptyList())
    val prompts: LiveData<List<String>> = _prompts

    private val _toast = MutableLiveData<String>("")
    val toast: LiveData<String> = _toast

    private val _aiUiState = MutableLiveData<UiState>(UiState.Initial)
    val aiUiState: LiveData<UiState> = _aiUiState

    private val _maleAverage = MutableLiveData<AveragePatient>(AveragePatient(patientSex = "Male"))
    val maleAverage: LiveData<AveragePatient> = _maleAverage

    private val _something = MutableStateFlow<AveragePatient>(AveragePatient(patientSex = "Female"))
    val something: MutableStateFlow<AveragePatient> = _something





    private val _femaleAverage = MutableLiveData<AveragePatient>(AveragePatient(patientSex = "Male"))
    val femaleAverage: LiveData<AveragePatient> = _femaleAverage

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )

    fun getAverageValues(patientViewModel: PatientViewModel){
        runBlocking{
            _maleAverage.postValue(patientViewModel.getPatientsAverageScores(patientSex = "Male"))
            _femaleAverage.postValue(patientViewModel.getPatientsAverageScores(patientSex = "Female"))
        }
    }

    suspend fun getPatterns(patients: List<Patient>) {
        try {
            val json = Gson().toJson(patients)

            val response = generativeModel.generateContent(
                content {
                    text("Give THREE interesting patterns in the following text, separated by a line break, within five or less sentences," +
                            "disregarding the phone numbers. Do not reply with Here is the pattern at the start of the message:" +
                            "for example: Users with high vegetable scores also scored high in fruit." +
                            "Here is the text: "
                            + json)
                }
            )
            response.text?.let { outputContent ->
                _prompts.postValue(outputContent.split("\n\n"))

            }
        } catch (e: Exception) {
            _prompts.postValue(listOf(e.localizedMessage ?: "Null"))

        }


    }

    fun getThreePatterns(patients: List<Patient>){
        _aiUiState.value = UiState.Loading
        _prompts.value = emptyList<String>()
        viewModelScope.launch(Dispatchers.IO) {
            getPatterns(patients)
            _aiUiState.postValue(UiState.Success("Done!"))
            Log.d("prompt", (_prompts.value ?: "None").toString())
        }
    }



}