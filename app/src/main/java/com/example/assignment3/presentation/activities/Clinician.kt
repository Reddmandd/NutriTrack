package com.example.assignment3.presentation.activities

import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.assignment3.data.model.AveragePatient
import com.example.assignment3.data.model.Fruit
import com.example.assignment3.data.model.MotivationalMessage
import com.example.assignment3.data.model.Nutrition
import com.example.assignment3.data.model.UiState
import com.example.assignment3.presentation.activities.ui.theme.Assignment3Theme
import com.example.assignment3.presentation.viewmodels.ClinicianLoginViewModel
import com.example.assignment3.presentation.viewmodels.ClinicianViewModel
import com.example.assignment3.presentation.viewmodels.NutricoachViewModel
import com.example.assignment3.presentation.viewmodels.PatientViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch





@Composable
@Preview(showBackground = true)
fun ClinicianScreen(innerPadding: PaddingValues, navController: NavController, clinicianViewModel: ClinicianViewModel, patientViewModel: PatientViewModel) {

    val uiState by clinicianViewModel.aiUiState.observeAsState()
    val maleAverage by clinicianViewModel.maleAverage.observeAsState(AveragePatient(patientSex = "Male"))
    val femaleAverage by clinicianViewModel.femaleAverage.observeAsState(AveragePatient(patientSex = "Female"))
    val prompts by clinicianViewModel.prompts.observeAsState(emptyList())
    val something by clinicianViewModel.something.collectAsState()


    LaunchedEffect(Unit) {
        clinicianViewModel.getAverageValues(patientViewModel = patientViewModel)
        patientViewModel.loadPatients()
    }
    val patients by patientViewModel.allPatients.observeAsState(emptyList())


    Scaffold(modifier = Modifier.padding(innerPadding)) { padding ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Clinician Dashboard",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )


            Spacer(Modifier.height(20.dp))
            ScoreDetailsRow("Average HEIFA (Male)", maleAverage.totalScore.toString())
            ScoreDetailsRow("Average HEIFA (Female)", femaleAverage.totalScore.toString())

            HorizontalDivider()

            Spacer(Modifier.height(20.dp))
            Button(
                modifier = Modifier.fillMaxWidth(0.5f),
                onClick = {
                    clinicianViewModel.getThreePatterns(patients)
                }
            ) {
                if (uiState is UiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White
                    )
                    Spacer(Modifier.width(5.dp))
                } else {
                    Icon(
                        imageVector = Icons.Sharp.ArrowForward,
                        contentDescription = "Details"
                    )
                }
                Text("Find Data Pattern")
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(prompts) { prompt ->
                    PromptCard(prompt)
                }
                item {
                    if(uiState == UiState.Loading){
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White
                        )
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End)
            {
                Button(
                    modifier = Modifier,
                    shape = RoundedCornerShape(5.dp),
                    onClick = {
                        navController.navigate("settings")
                    }
                ) {
                    Text(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp), text = "Done",
                        fontSize = 20.sp)
                }
            }







        }
    }
}


@Composable
fun ScoreDetailsRow(label: String = "FRUIT", value: String = "VALUE") {
    Surface(
        modifier = Modifier.border(BorderStroke(2.dp, Color.Gray), shape = RoundedCornerShape(10.dp))
    ) {
        Row(modifier = Modifier.padding(vertical = 4.dp)) {
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = label, modifier = Modifier.weight(1f))
            Text(text = ":", modifier = Modifier.padding(horizontal = 8.dp))
            Text(text = value, modifier = Modifier.weight(2f))
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}



