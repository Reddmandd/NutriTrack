package com.example.assignment3.presentation.activities

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.assignment3.data.model.AuthManager
import com.example.assignment3.data.model.Patient
import com.example.assignment3.presentation.activities.ui.theme.Assignment3Theme
import com.example.assignment3.presentation.viewmodels.InsightsViewModel
import com.example.assignment3.presentation.viewmodels.PatientViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@Preview(showBackground = true)
@Composable
fun InsightsScreen(innerPadding: PaddingValues, patientViewModel: PatientViewModel, insightsViewModel: InsightsViewModel, navController: NavController) {

    Scaffold(
        modifier = Modifier.padding(innerPadding).padding(10.dp).fillMaxSize()
    ) { innerPadding ->

        LaunchedEffect(Unit) {
            insightsViewModel.getPatient(patientViewModel)
        }
        val context = LocalContext.current
        val patient = insightsViewModel.patient.value

        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                Text("Insights: Food Score", fontWeight = FontWeight.Bold, fontSize = 30.sp)
            }
            Spacer(modifier = Modifier.padding(20.dp))
            MyProgressBar(patient.vegetableScore, 10f, "Vegetables")
            MyProgressBar(patient.fruitScore, 10f, "Fruits")
            MyProgressBar(patient.grainScore, 10f, "Grains & Cereals")
            MyProgressBar(patient.wholeGrainScore, 10f, "Whole Grains")
            MyProgressBar(patient.meatScore, 10f, "Meat & Alternatives")
            MyProgressBar(patient.dairyScore, 10f, "Dairy")
            MyProgressBar(patient.waterScore, 5f, "Water")
            MyProgressBar(patient.unsaturatedFatScore, 10f, "Unsaturated Fats")
            MyProgressBar(patient.sodiumScore, 10f, "Sodium")
            MyProgressBar(patient.sugarScore, 10f, "Sugar")
            MyProgressBar(patient.alcoholScore, 10f, "Alcohol")
            MyProgressBar(patient.discretionaryFoodScore, 10f, "Discretionary\nFoods")


            Spacer(modifier = Modifier.padding(20.dp))
            Text("Total Food Quality Score:")
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgressIndicator(

                    progress = { (patient.totalScore / 100f).toFloat() }
                )
                Spacer(modifier = Modifier.weight(1f))
                Text("${patient.totalScore.toString()}/100.0")
            }
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {
                    val shareIntent = Intent(ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "My HEIFA score is ${patient.totalScore.toString()} !")
                    context.startActivity(Intent.createChooser(shareIntent, "Share text via"))
                }) {
                    Text("Share with someone!")
                }
                Button(onClick = {
                    navController.navigate("nutricoach")
                }) {
                    Text("Improve my diet!")
                }
            }
        }
    }
}

@Composable
fun MyProgressBar(currentValue: Float, maxValue: Float, name: String){
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = name, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(1f))
        LinearProgressIndicator(

            progress = {currentValue / maxValue},
            modifier = Modifier.padding(10.dp).width(190.dp)
        )
        Text("$currentValue/${maxValue}", fontSize = 13.sp)
    }
}