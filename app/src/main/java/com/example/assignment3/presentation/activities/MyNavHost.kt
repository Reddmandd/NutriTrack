package com.example.assignment3.presentation.activities

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.assignment3.presentation.viewmodels.ChangeNameViewModel
import com.example.assignment3.presentation.viewmodels.ClinicianLoginViewModel
import com.example.assignment3.presentation.viewmodels.ClinicianViewModel
import com.example.assignment3.presentation.viewmodels.FoodIntakeViewModel
import com.example.assignment3.presentation.viewmodels.ForgotPasswordViewModel
import com.example.assignment3.presentation.viewmodels.HomeScreenViewModel
import com.example.assignment3.presentation.viewmodels.InsightsViewModel
import com.example.assignment3.presentation.viewmodels.MessageViewModel
import com.example.assignment3.presentation.viewmodels.NutricoachViewModel
import com.example.assignment3.presentation.viewmodels.PatientViewModel
import com.example.assignment3.presentation.viewmodels.QuestionaireViewModel
import com.example.assignment3.presentation.viewmodels.SettingsViewModel

@Composable
fun MyNavHost(innerPadding: PaddingValues,
              navController: NavHostController,
              patientViewModel: PatientViewModel,
              questionaireViewModel: QuestionaireViewModel,
              foodIntakeViewModel: FoodIntakeViewModel,
              nutricoachViewModel: NutricoachViewModel,
              homeScreenViewModel: HomeScreenViewModel,
              settingsViewModel: SettingsViewModel,
              clinicianLoginViewModel: ClinicianLoginViewModel,
              messageViewModel: MessageViewModel,
              clinicianViewModel: ClinicianViewModel,
              changeNameViewModel: ChangeNameViewModel,
              insightsViewModel: InsightsViewModel,
              forgotPasswordViewModel: ForgotPasswordViewModel) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(innerPadding, patientViewModel, navController, homeScreenViewModel)
        }

        composable("insights") {
            InsightsScreen(innerPadding, patientViewModel, insightsViewModel, navController)
        }

        composable("nutricoach") {
            NutricoachScreen(innerPadding, nutricoachViewModel, patientViewModel, messageViewModel)
        }

        composable("questionaire") {
            questionaireViewModel.getInitialValues(foodIntakeViewModel)
            Questionnaire(innerPadding, questionaireViewModel, patientViewModel, foodIntakeViewModel, navController)
        }

        composable("settings") {
            SettingsScreen(innerPadding, settingsViewModel, patientViewModel, navController)
        }

        composable("clinicianLogin") {
            ClinicianLogin(innerPadding, clinicianLoginViewModel, navController)
        }

        composable("clinician") {
            ClinicianScreen(innerPadding, navController, clinicianViewModel, patientViewModel)
        }

        composable("changeName") {
            ChangeNameScreen(innerPadding, changeNameViewModel, patientViewModel, navController)
        }

        composable("changePassword") {
            ChangePasswordScreen(Modifier.padding(innerPadding), patientViewModel, forgotPasswordViewModel, "Change Password", navController)
        }
    }
}