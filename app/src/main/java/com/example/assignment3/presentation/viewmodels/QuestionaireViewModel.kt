package com.example.assignment3.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.RequiresApi
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.assignment3.data.model.AuthManager
import com.example.assignment3.data.model.FoodIntake
import com.example.assignment3.data.model.UiState
import com.example.assignment3.presentation.activities.HomeScreen
import com.example.assignment3.presentation.activities.Login
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class   QuestionaireViewModel(application: Application) : AndroidViewModel(application) {

    var firstLaunch by mutableStateOf(true)
    var rFruits by mutableStateOf(false)
    var rVegetable by mutableStateOf(false)
    var rGrains by mutableStateOf(false)
    var rRedmeat by mutableStateOf(false)
    var rSeafood by mutableStateOf(false)
    var rPoultry by mutableStateOf(false)
    var rFish by mutableStateOf(false)
    var rEggs by mutableStateOf(false)
    var rNutseeds by mutableStateOf(false)
    var rPersona by mutableStateOf("Choose your persona")
    var rLargestMealTime: MutableState<String?> = mutableStateOf("00:00")
    var rSleepingTime: MutableState<String?> = mutableStateOf("00:00")
    var rWakingTime: MutableState<String?> = mutableStateOf("00:00")

    var isExpanded by mutableStateOf(false)

    var isModalExpanded1 = mutableStateOf(false)
    var showDialog1 = mutableStateOf(false)

    var isModalExpanded2 = mutableStateOf(false)
    var showDialog2 = mutableStateOf(false)

    var isModalExpanded3 = mutableStateOf(false)
    var showDialog3 = mutableStateOf(false)

    var isModalExpanded4 = mutableStateOf(false)
    var showDialog4 = mutableStateOf(false)

    var isModalExpanded5 = mutableStateOf(false)
    var showDialog5 = mutableStateOf(false)

    var isModalExpanded6 = mutableStateOf(false)
    var showDialog6 = mutableStateOf(false)


    private val _toast = MutableLiveData<String>("")
    val toast: LiveData<String> = _toast

    private val _finishQuestionnaire = MutableLiveData<Boolean>(false)
    val finishQuestionnaire: LiveData<Boolean> = _finishQuestionnaire





    fun getInitialValues(foodIntakeViewModel: FoodIntakeViewModel) {

        runBlocking {
            var patientId: Int = AuthManager.getPatientId() ?: 0
            var foodIntake = foodIntakeViewModel.getPatientById(patientId)


            if (firstLaunch && foodIntake != null) {
                firstLaunch = false
                rFruits = foodIntake.fruits
                rVegetable = foodIntake.vegetable
                rGrains = foodIntake.grains
                rRedmeat = foodIntake.redmeat
                rSeafood = foodIntake.seafood
                rPoultry = foodIntake.poultry
                rFish = foodIntake.fish
                rEggs = foodIntake.eggs
                rNutseeds = foodIntake.nutseeds
                rPersona = foodIntake.persona
                rLargestMealTime.value = foodIntake.largestMealTime
                rSleepingTime.value = foodIntake.sleepingTime
                rWakingTime.value = foodIntake.wakingTime
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun confirmValues(foodIntakeViewModel: FoodIntakeViewModel){
        runBlocking{
            var patientId: Int = AuthManager.getPatientId() ?: 0

            val formatter = DateTimeFormatter.ofPattern("HH:mm")

            var largestMealTimeString = rLargestMealTime.value.toString()

            var sleepingTimeString = rSleepingTime.value.toString()

            var wakingTimeString = rWakingTime.value.toString()

            Log.d("Largest Meal Time", largestMealTimeString)
            Log.d("Sleeping Time", sleepingTimeString)
            Log.d("Waking Time", wakingTimeString)
            Log.d("Toast?", toast.value.toString())

            val mealTime = LocalTime.parse(largestMealTimeString, formatter)
            val wakingTime = LocalTime.parse(wakingTimeString, formatter)
            val sleepingTime = LocalTime.parse(sleepingTimeString, formatter)


            if (sleepingTime < wakingTime && mealTime < wakingTime ||
                sleepingTime > wakingTime && (mealTime > sleepingTime || mealTime < wakingTime)){
                _toast.value = "Time of the largest meal should not be after sleeping, or before waking up!"
                _finishQuestionnaire.value = false
            }
            else if((mealTime == wakingTime) || (mealTime == sleepingTime) || (sleepingTime == wakingTime)){
                _toast.value = "None of the times inputted should share the same value!"
                _finishQuestionnaire.value = false
            }
            else if(rPersona == "Choose your persona"){
                _toast.value = "Persona has yet to be chosen!"
                _finishQuestionnaire.value = false
            }
            else {
                firstLaunch = true
                var foodIntake = foodIntakeViewModel.getPatientById(patientId)
                if (foodIntake == null) {
                    foodIntake = FoodIntake(
                        patientId = patientId,
                        fruits = rFruits,
                        vegetable = rVegetable,
                        grains = rGrains,
                        redmeat = rRedmeat,
                        seafood = rSeafood,
                        poultry = rPoultry,
                        fish = rFish,
                        eggs = rEggs,
                        nutseeds = rNutseeds,
                        persona = rPersona,
                        largestMealTime = largestMealTimeString,
                        sleepingTime = sleepingTimeString,
                        wakingTime = wakingTimeString
                    )
                    foodIntakeViewModel.insert(foodIntake)
                } else {
                    Log.d("FRUITS?", rFruits.toString())
                    foodIntake.fruits = rFruits
                    foodIntake.vegetable = rVegetable
                    foodIntake.grains = rGrains
                    foodIntake.redmeat = rRedmeat
                    foodIntake.seafood = rSeafood
                    foodIntake.poultry = rPoultry
                    foodIntake.fish = rFish
                    foodIntake.eggs = rEggs
                    foodIntake.nutseeds = rNutseeds
                    foodIntake.persona = rPersona
                    foodIntake.largestMealTime = largestMealTimeString
                    foodIntake.sleepingTime = sleepingTimeString
                    foodIntake.wakingTime = wakingTimeString

                    foodIntakeViewModel.update(foodIntake)
                }
                _toast.value = "Questionnaire successfully updated"
                _finishQuestionnaire.value = true
            }

        }
    }

    fun getTimeString(mHour: Int, mMinute: Int): Pair<String, String> {
        var mHourString: String
        var mMinuteString: String

        if (mHour.toString().length == 1) {
            mHourString = "0$mHour"
        } else {
            mHourString = mHour.toString()
        }

        if (mMinute.toString().length == 1) {
            mMinuteString = "0$mMinute"
        } else {
            mMinuteString = mMinute.toString()
        }

        return Pair(mHourString, mMinuteString)
    }

    fun hasToast(): Boolean{
        Log.d("Has Toast?", (_toast.value != "").toString())
        return _toast.value != ""
    }

    fun clearToast(){
        _toast.value = ""
    }

    fun logout(context: Context){
        val intent = Intent(context, Login::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
        AuthManager.logout(context)
    }



    fun goToNewScreen(context: Context, navController: NavController?) {
        Log.d("Is this happening?", "")
        if (_finishQuestionnaire.value == true) {
            _finishQuestionnaire.postValue(false)
            if (navController != null) {
                navController.navigate("home")
            } else {
                context.startActivity(Intent(context, HomeScreen::class.java))
            }


        }
    }
}




