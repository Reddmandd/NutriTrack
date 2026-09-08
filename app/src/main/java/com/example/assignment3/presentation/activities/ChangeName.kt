package com.example.assignment3.presentation.activities

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.assignment3.presentation.viewmodels.ChangeNameViewModel
import com.example.assignment3.presentation.viewmodels.PatientViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ChangeNameScreen(innerPadding: PaddingValues, changeNameViewModel: ChangeNameViewModel,
                     patientViewModel: PatientViewModel, navController: NavController) {
    val context = LocalContext.current
    val toast by changeNameViewModel.toast.observeAsState("")


    LaunchedEffect(toast) {
        if(changeNameViewModel.checkToast()) {
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
            changeNameViewModel.clearToast()
        }
    }
    Scaffold(modifier = Modifier.padding(innerPadding)) { padding ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Text(
                    text = "Change Name",
                    fontSize = 30.sp
                )

                Spacer(
                    modifier = Modifier.height(24.dp),
                )


                OutlinedTextField(
                    value = changeNameViewModel.newName,
                    onValueChange = { changeNameViewModel.newName = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(0.75f),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            changeNameViewModel.registerPatient(patientViewModel)
                        }
                    }
                ) {
                    Text("Change Name")
                }

                Button(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    onClick = {
                        changeNameViewModel.finishChangeName()
                        navController.navigate("settings")
                    }
                ) {
                    Text("Done")
                }

            }
        }
    }
}