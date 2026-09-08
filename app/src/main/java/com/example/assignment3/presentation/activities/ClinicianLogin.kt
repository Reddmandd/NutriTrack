package com.example.assignment3.presentation.activities

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.assignment3.presentation.viewmodels.ClinicianLoginViewModel


@Composable
fun ClinicianLogin(innerPadding: PaddingValues, clinicianLoginViewModel: ClinicianLoginViewModel, navController: NavController){
    val context = LocalContext.current
    Scaffold(modifier = Modifier.padding(innerPadding).fillMaxSize()) { padding ->
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Clinician Login",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(24.dp),
            )

            OutlinedTextField(
                value = clinicianLoginViewModel.password,
                onValueChange = { clinicianLoginViewModel.password = it },
                label = { Text("Clinician Key") },
                modifier = Modifier.fillMaxWidth(0.75f),
                maxLines = 1
            )

            Button(
                modifier = Modifier.fillMaxWidth(0.5f),
                onClick = {
                    val (isLogin, toast) = clinicianLoginViewModel.checkPassword()
                    if (isLogin) {
                        navController.navigate("clinician")
                    }
                    Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
                }
            ) {
                Text("Clinician Login")
            }
        }
    }

}