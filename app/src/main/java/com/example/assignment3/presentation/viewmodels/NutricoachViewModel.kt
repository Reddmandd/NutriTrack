package com.example.assignment3.presentation.viewmodels

import FruityViceRepository
import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.google.ai.client.generativeai.GenerativeModel

import com.google.ai.client.generativeai.type.content
import androidx.lifecycle.viewModelScope
import coil3.compose.rememberAsyncImagePainter
import com.example.assignment3.BuildConfig
import com.example.assignment3.data.dao.FruityViceAPI
import com.example.assignment3.data.model.AuthManager
import com.example.assignment3.data.model.Fruit
import com.example.assignment3.data.model.Nutrition
import com.example.assignment3.data.model.Patient
import com.example.assignment3.data.model.UiState
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NutricoachViewModel(application: Application) : AndroidViewModel(application)  {
    private val _uiState = MutableLiveData<UiState>(UiState.Initial)
    val uiState: LiveData<UiState> = _uiState

    var showDialog by mutableStateOf(false)

    private val fruityViceRepository = FruityViceRepository()

    private val _fruit = MutableLiveData<Fruit>(Fruit("-", "-", "-", "-", Nutrition(0f, 0f, 0f, 0f, 0f)))
    val fruit: LiveData<Fruit> = _fruit

    var fruitName by mutableStateOf("")

    private val _toast = MutableLiveData<String>("")
    val toast: LiveData<String> = _toast

    private val _aiUiState = MutableLiveData<UiState>(UiState.Initial)
    val aiUiState: LiveData<UiState> = _aiUiState

    private val _aiResult = MutableLiveData<String>("")
    val aiResult: LiveData<String> = _aiResult


    var imageUrl = mutableStateOf("https://picsum.photos/200?random=${System.currentTimeMillis()}")


    fun checkOptimalScore(patientViewModel: PatientViewModel): Boolean{
        var patient: Patient
        runBlocking {
            patient = patientViewModel.getPatientById(AuthManager.getPatientId() ?: -1)
            Log.d("Checking Optimal..", (patient.fruitVariety > 5 && patient.fruitServing >= 2).toString())
        }

        return (patient.fruitVariety > 5 && patient.fruitServing >= 2)
    }

    fun searchFruit(){
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val result = fruityViceRepository.searchFruit(fruitName)
            _fruit.value = result

            if (result.name != "-"){
                Log.d("Posting Toast!", _toast.value ?: "None")
                _toast.postValue("Loading Success!")
                _uiState.value = UiState.Success("Loading Success!")
            }
            else{
                _toast.postValue("Fruit not found!")
                _uiState.value = UiState.Error("Fruit not found!")
            }
        }
    }

    fun checkToast(): Boolean{
        Log.d("Toast Value", _toast.value.toString())
        return _toast.value != ""
    }

    fun clearToast(){
        _toast.postValue("")
    }

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )

    fun getMotivationalMessage( patientViewModel: PatientViewModel, messageViewModel: MessageViewModel
    ) {
        _aiUiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val json = Gson().toJson(patientViewModel.getPatientById(AuthManager.getPatientId() ?: -1))
                val response = generativeModel.generateContent(
                    content {
                        text("Generate a short encouraging message to help someone improve their fruit intake," +
                                "if their fruit variety > 5 and fruit serving score >= 2, congratulate them instead."
                                + json)
                    }
                )
                response.text?.let { outputContent ->
                    Log.d("Response", outputContent)
                    _aiUiState.postValue(UiState.Success(outputContent))
                    _aiResult.postValue(outputContent)
                    messageViewModel.insertMessage(outputContent)
                }

            } catch (e: Exception) {
                _aiUiState.postValue(UiState.Error(e.localizedMessage ?: ""))
            }

        }
    }





}