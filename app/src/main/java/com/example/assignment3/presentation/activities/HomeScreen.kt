package com.example.assignment3.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignment3.presentation.activities.ui.theme.Assignment3Theme
import com.example.assignment3.R
import com.example.assignment3.data.model.AuthManager
import com.example.assignment3.data.model.Patient
import com.example.assignment3.presentation.viewmodels.ChangeNameViewModel
import com.example.assignment3.presentation.viewmodels.ClinicianLoginViewModel
import com.example.assignment3.presentation.viewmodels.ClinicianViewModel
import com.example.assignment3.presentation.viewmodels.FoodIntakeViewModel
import com.example.assignment3.presentation.viewmodels.ForgotPasswordViewModel
import com.example.assignment3.presentation.viewmodels.HomeScreenViewModel
import com.example.assignment3.presentation.viewmodels.InsightsViewModel
import com.example.assignment3.presentation.viewmodels.MainActivityViewModel
import com.example.assignment3.presentation.viewmodels.MessageViewModel
import com.example.assignment3.presentation.viewmodels.NutricoachViewModel
import com.example.assignment3.presentation.viewmodels.PatientViewModel
import com.example.assignment3.presentation.viewmodels.QuestionaireViewModel
import com.example.assignment3.presentation.viewmodels.SettingsViewModel
import kotlinx.coroutines.runBlocking
import kotlin.getValue


class HomeScreen : ComponentActivity() {
    private val forgotPasswordViewModel : ForgotPasswordViewModel by viewModels()
    private val patientViewModel : PatientViewModel by viewModels()
    private val foodIntakeViewModel : FoodIntakeViewModel by viewModels()
    private val questionaireViewModel : QuestionaireViewModel by viewModels()
    private val nutricoachViewModel : NutricoachViewModel by viewModels()
    private val homeScreenViewModel : HomeScreenViewModel by viewModels()
    private val settingsViewModel : SettingsViewModel by viewModels()
    private val clinicianLoginViewModel: ClinicianLoginViewModel by viewModels()
    private val messageLoginViewModel: MessageViewModel by viewModels()
    private val clinicianViewModel: ClinicianViewModel by viewModels()
    private val changeNameViewModel: ChangeNameViewModel by viewModels()
    private val insightsViewModel: InsightsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment3Theme {
                val navController: NavHostController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        MyBottomBar(navController)
                    }
                ) { innerPadding ->
                    Column() {
                        MyNavHost(innerPadding, navController, patientViewModel, questionaireViewModel,
                            foodIntakeViewModel, nutricoachViewModel, homeScreenViewModel, settingsViewModel,
                            clinicianLoginViewModel, messageLoginViewModel, clinicianViewModel, changeNameViewModel,
                            insightsViewModel, forgotPasswordViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun MyBottomBar(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf(0) }
    var items = listOf(
        "Home",
        "Insights",
        "Nutricoach",
        "Settings"
    )
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    when (item) {
                        "Home" -> Icon(Icons.Filled.Home, contentDescription = "Home")
                        "Insights" -> Icon(
                            Icons.Outlined.Info,
                            contentDescription = "Insights"
                        )
                        "Nutricoach" -> Icon(
                            Icons.Filled.Face,
                            contentDescription = "NutriCoach"
                        )

                        "Settings" -> Icon(
                            Icons.Filled.Settings,
                            contentDescription = "Settings"
                        )

                    }
                },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item)
                }
            )
        }
    }
}

@Composable
fun HomeScreen(innerPadding: PaddingValues, patientViewModel: PatientViewModel, navController: NavHostController,
               homeScreenViewModel: HomeScreenViewModel) {

    LaunchedEffect(Unit) {
        homeScreenViewModel.getPatient(patientViewModel)}

    val totalScore = homeScreenViewModel.patient.value.totalScore
    val name = homeScreenViewModel.patient.value.name
    Scaffold(modifier = Modifier.padding(innerPadding)) { padding ->

        Column (modifier = Modifier.padding(10.dp)){
            Text("Hello,", color = Color.Gray)
            Text("$name", fontWeight = FontWeight.Bold, fontSize = 40.sp)
            Row(){
                Text(fontSize = 11.sp,
                    text = "You've already filled in your Food Intake\n" +
                            "Questionaire, but you can change your details here:",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        navController.navigate("questionaire")
                    }, shape = RoundedCornerShape(10.dp),
                ) {
                    Text("Edit")
                }
            }
            Column (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                Image(
                    painter = painterResource(id = R.drawable.home_screen_image),
                    contentDescription = "home_screen",
                    modifier = Modifier.size(250.dp)
                )}
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("My Score", fontWeight = FontWeight.Bold, fontSize = 30.sp)
                Text("See All Scores>", color = Color.Gray, modifier = Modifier.clickable{
                    navController.navigate("insights")
                })


            }

            Row(){
                Icons.Filled.AddCircle
                Text("Your Food Quality Score")
                Spacer(modifier = Modifier.weight(1f))
                Text("$totalScore/100", color = Color.DarkGray)
            }
            Spacer(modifier = Modifier.padding(20.dp))
            HorizontalDivider()
            Text("What is the Food Quality Score?", fontWeight = FontWeight.Bold, fontSize = 25.sp)
            Text("Your Food Quality score provides a snapshot of how well your eating" +
                    " patterns align with established food guidelines, helping you identify both" +
                    " strengths and opportunities for improvements in your diet." +
                    "\n\n" +
                    "This personalized measurement considers various food groups including" +
                    " vegetables, fruits, whole grains, and proteins to give you practical" +
                    " insights for making healthier food choices.", fontSize = 15.sp)




        }
    }

}
