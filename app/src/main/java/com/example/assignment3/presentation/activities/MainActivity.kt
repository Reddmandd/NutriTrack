package com.example.assignment3.presentation.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import com.example.assignment3.data.model.AuthManager
import com.example.assignment3.presentation.viewmodels.*
import com.example.assignment3.ui.theme.Assignment3Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private val patientViewModel: PatientViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment3Theme {
                val context = LocalContext.current
                mainActivityViewModel.firstLaunch(context, patientViewModel)

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (!mainActivityViewModel.checkLoggedIn()) {
                        Log.d("Current User", AuthManager.getPatientId().toString())
                        WelcomeScreen(innerPadding = innerPadding, context = context)
                    } else {
                        val intent = Intent(context, Questionnaire()::class.java)
                        context.startActivity(intent)
                    }

                }
            }
        }
    }
}

