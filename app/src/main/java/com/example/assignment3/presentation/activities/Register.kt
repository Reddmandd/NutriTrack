package com.example.assignment3.presentation.activities

import android.app.Activity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import com.example.assignment3.presentation.viewmodels.LoginViewModel
import com.example.assignment3.presentation.viewmodels.RegisterViewModel
import kotlin.getValue
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.assignment3.data.model.AuthManager
import com.example.assignment3.presentation.viewmodels.PatientViewModel
import com.example.assignment3.data.model.Patient
import com.example.assignment3.ui.theme.Assignment3Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Register : ComponentActivity() {

    private val patientViewModel: PatientViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegisterScreen(patientViewModel = patientViewModel, registerViewModel = registerViewModel)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(modifier: Modifier = Modifier, patientViewModel: PatientViewModel, registerViewModel: RegisterViewModel) {
    val context = LocalContext.current
    patientViewModel.loadPatients()
    val patients by patientViewModel.allPatients.observeAsState(emptyList())

    val toast by registerViewModel.toast.observeAsState("")

    LaunchedEffect(toast) {
        if(registerViewModel.checkToast()) {
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
            registerViewModel.clearToast()
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = "Register",
                fontSize = 30.sp
            )

            Spacer(
                modifier = Modifier.height(24.dp),
            )

            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(0.75f),
                expanded = registerViewModel.is_expanded,
                onExpandedChange = { registerViewModel.is_expanded = it }
            ) {

                TextField(
                    value = registerViewModel.selected_id.toString(),
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Select an ID") },
                    trailingIcon = {

                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = registerViewModel.is_expanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = registerViewModel.is_expanded,
                    onDismissRequest = { registerViewModel.is_expanded = false }
                ) {

                    patients.forEach { patient ->
                        DropdownMenuItem(
                            text = { Text(patient.patientId.toString()) },
                            onClick = {
                                registerViewModel.selected_id = patient.patientId.toString()
                                registerViewModel.is_expanded = false
                            }
                        )

                    }
                }
            }

            OutlinedTextField(
                value = registerViewModel.name,
                onValueChange = { registerViewModel.name = it },
                label = { Text("Name (As per IC/Passport)") },
                modifier = Modifier.fillMaxWidth(0.75f),
                maxLines = 1
            )

            OutlinedTextField(
                value = registerViewModel.password,
                onValueChange = { registerViewModel.password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(0.75f),
                maxLines = 1
            )

            OutlinedTextField(
                value = registerViewModel.confirm_password,
                onValueChange = { registerViewModel.confirm_password = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(0.75f),
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(0.5f),
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        registerViewModel.registerPatient(patientViewModel)
                    }
                }
            ) {
                Text("Register")
            }

            Button(
                modifier = Modifier.fillMaxWidth(0.5f),
                onClick = {
                    val activity = context as? Activity
                    activity?.finish()
                }
            ) {
                Text("Continue")
            }

        }
    }
}