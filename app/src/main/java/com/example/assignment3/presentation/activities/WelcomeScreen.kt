package com.example.assignment3.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment3.ui.theme.Assignment3Theme

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier, innerPadding: PaddingValues, context: Context){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(innerPadding).fillMaxSize()
    ) {
        Text(
            text = "NutriTrack",
            fontWeight = FontWeight.Bold,
            fontSize = 60.sp
        )

        Text(
            text = "This app provides general health and nutrition information for " +
                    "educational purposes only. It is not intended as medical advice, " +
                    "diagnosis, or treatment. Always consult a qualified healthcare " +
                    "professional before making any changes to your diet, exercise, or " +
                    "health regimen.\n" +
                    "Use this app at your own risk.\n" +
                    "If you’d like to an Accredited Practicing Dietitian (APD), please " +
                    "visit the Monash Nutrition/Dietetics Clinic (discounted rates for " +
                    "students):\n" +
                    "https://www.monash.edu/medicine/scs/nutrition/clinics/nutrition",
            color = Color.Blue,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.fillMaxWidth(0.9f)

        )
        Button(onClick = {
            context.startActivity(Intent(context, Login::class.java))
        }, modifier = Modifier.padding(30.dp)) {
            Text("Login")
        }


    }
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ){
        Text(text = "Made by Ambrose Soon Jia Xun (35379669)",
            Modifier.padding(30.dp))
    }
}