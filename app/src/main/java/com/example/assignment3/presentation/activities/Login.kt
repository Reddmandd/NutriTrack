package com.example.assignment3.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment3.presentation.viewmodels.LoginViewModel
import com.example.assignment3.presentation.viewmodels.PatientViewModel
import com.example.assignment3.ui.theme.Assignment3Theme


class Login : ComponentActivity() {

    private val patientViewModel: PatientViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(patientViewModel = patientViewModel, loginViewModel = loginViewModel)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(modifier: Modifier = Modifier, patientViewModel: PatientViewModel, loginViewModel: LoginViewModel){
    val context = LocalContext.current
    patientViewModel.loadPatients()
    val patients by patientViewModel.allPatients.observeAsState(emptyList())
    val toast by loginViewModel.toast.observeAsState("")


    LaunchedEffect(toast) {
        if(loginViewModel.hasToast()) {
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
            loginViewModel.clearToast()
        }
        loginViewModel.goToNextScreen(context)
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        Column(
            modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = "Login",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(24.dp),
            )

            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(0.75f),
                expanded = loginViewModel.is_expanded,
                onExpandedChange = { loginViewModel.is_expanded = it }
            ) {

                TextField(
                    value = loginViewModel.selected_id.toString(),
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Select an ID") },
                    trailingIcon = {

                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = loginViewModel.is_expanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = loginViewModel.is_expanded,
                    onDismissRequest = { loginViewModel.is_expanded = false }
                ) {

                    patients.forEach { patient ->
                        DropdownMenuItem(
                            text = { Text(patient.patientId.toString()) },
                            onClick = {
                                loginViewModel.selected_id = patient.patientId.toString()
                                loginViewModel.is_expanded = false
                            }
                        )

                    }
                }
            }
            OutlinedTextField(
                value = loginViewModel.password,
                onValueChange = {loginViewModel.password = it},
                label = {Text("Password")},
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(0.75f),
                maxLines = 1
            )

            Button(modifier = Modifier,
                onClick = {
                    val intent = Intent(context, ForgotPassword::class.java)
                    context.startActivity(intent)
                }
            ){
                Text("Forgot Password?")
            }
            HorizontalDivider()
            Spacer(modifier = Modifier.height(24.dp))
            Button(modifier = Modifier.fillMaxWidth(0.5f),
                onClick = {
                    loginViewModel.verifyPatient(patientViewModel, context)
                }
            ){
                Text("Login")
            }

            Button(modifier = Modifier.fillMaxWidth(0.5f),
                onClick = {
                    val intent = Intent(context, Register::class.java)
                    context.startActivity(intent)
                }
            ){
                Text("Register")
            }

        }
    }
}
