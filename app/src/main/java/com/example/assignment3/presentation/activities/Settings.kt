package com.example.assignment3.presentation.activities

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.material.icons.sharp.ArrowForward
import androidx.compose.material.icons.sharp.Face
import androidx.compose.material.icons.sharp.Lock
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.assignment3.presentation.viewmodels.PatientViewModel
import com.example.assignment3.presentation.viewmodels.SettingsViewModel


@Composable
fun SettingsScreen(innerPadding: PaddingValues, settingsViewModel: SettingsViewModel, patientViewModel: PatientViewModel,
                   navController: NavController){
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        settingsViewModel.getPatient(patientViewModel)}

    Scaffold(modifier = Modifier.padding(innerPadding)) { padding ->
        Column(
            Modifier
                .fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = "Settings",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                Text(
                    text = "ACCOUNT",
                    fontSize = 15.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth().clickable{
                navController.navigate("changeName")
            },
                horizontalArrangement = Arrangement.Start) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Account Name",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.width(30.dp))

                Column(modifier = Modifier.height(40.dp), verticalArrangement = Arrangement.Center) {
                    Text(text = settingsViewModel.name,
                        fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Account Name",
                    modifier = Modifier.size(40.dp)
                )

            }

            SettingsRow(settingsViewModel.patientId, "ID", Icons.Sharp.AccountCircle)
            SettingsRow(settingsViewModel.phoneNumber, "Phone Number", Icons.Outlined.Phone)
            Spacer(Modifier.height(20.dp))

            HorizontalDivider()

            Spacer(Modifier.height(30.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                Text(
                    text = "OTHER SETTINGS",
                    fontSize = 15.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth().clickable{
                settingsViewModel.logout(context)
                },
                horizontalArrangement = Arrangement.Start) {
                Icon(
                    imageVector = Icons.Sharp.Lock,
                    contentDescription = "Logout",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.width(30.dp))

                Column(modifier = Modifier.height(40.dp), verticalArrangement = Arrangement.Center) {
                    Text(text = "Logout",
                        fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Sharp.ArrowForward,
                    contentDescription = "Go To Logout",
                    modifier = Modifier.size(40.dp)
                )

            }

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth().clickable{
                    navController.navigate("clinicianLogin")
                },
                horizontalArrangement = Arrangement.Start) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "Clinician",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.width(30.dp))

                Column(modifier = Modifier.height(40.dp), verticalArrangement = Arrangement.Center) {
                    Text(text = "Clinician Login",
                        fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Sharp.ArrowForward,
                    contentDescription = "Go To Clinician",
                    modifier = Modifier.size(40.dp)
                )


            }
            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth().clickable{
                    navController.navigate("changePassword")
                },
                horizontalArrangement = Arrangement.Start) {
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = "Clinician",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.width(30.dp))

                Column(modifier = Modifier.height(40.dp), verticalArrangement = Arrangement.Center) {
                    Text(text = "Change Password",
                        fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Sharp.ArrowForward,
                    contentDescription = "Change Password",
                    modifier = Modifier.size(40.dp)
                )


            }


        }
    }

}

@Composable
fun SettingsRow(text: String, description: String, imageVector: ImageVector){

    Spacer(Modifier.height(20.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        Icon(
            imageVector = imageVector,
            contentDescription = description,
            modifier = Modifier.size(40.dp)
        )
        Spacer(Modifier.width(30.dp))

        Column(modifier = Modifier.height(40.dp), verticalArrangement = Arrangement.Center) {
            Text(text = text,
                fontSize = 20.sp)
        }

    }
}
