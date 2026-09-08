package com.example.assignment3.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.saveable.rememberSaveable
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
import coil3.compose.AsyncImage
import com.example.assignment3.data.model.Fruit
import com.example.assignment3.data.model.MotivationalMessage
import com.example.assignment3.data.model.Nutrition
import com.example.assignment3.data.model.UiState
import com.example.assignment3.presentation.activities.ui.theme.Assignment3Theme
import com.example.assignment3.presentation.viewmodels.MessageViewModel
import com.example.assignment3.presentation.viewmodels.NutricoachViewModel
import com.example.assignment3.presentation.viewmodels.PatientViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun NutricoachScreen(innerPadding: PaddingValues, nutricoachViewModel: NutricoachViewModel, patientViewModel: PatientViewModel,
                     messageViewModel: MessageViewModel) {
    var checkOptimal by remember { mutableStateOf(false) }
    val aiUiState by nutricoachViewModel.aiUiState.observeAsState()
    val result by nutricoachViewModel.aiResult.observeAsState("")
    val messages by messageViewModel.getAllMessages().observeAsState(emptyList())

    LaunchedEffect(Unit) {
        checkOptimal = nutricoachViewModel.checkOptimalScore(patientViewModel)
    }

    Scaffold(Modifier.fillMaxSize().padding(innerPadding)) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Column(modifier = Modifier.weight(1f).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Nutricoach",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                if (!checkOptimal) {
                    SearchNutricoachScreen(
                        padding,
                        nutricoachViewModel,
                        patientViewModel,
                        messageViewModel
                    )
                } else {
                    AsyncImage(
                        model = nutricoachViewModel.imageUrl.value,
                        contentDescription = "Random Image",
                        modifier = Modifier.size(200.dp)
                    )
                }
            }

            Column(modifier = Modifier.weight(0.5f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        onClick = {
                            nutricoachViewModel.getMotivationalMessage(
                                patientViewModel,
                                messageViewModel
                            )
                        }
                    ) {
                        Text("Motivational Message (AI)")
                    }
                }
                if (aiUiState is UiState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    Text(
                        text = result,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                            .fillMaxSize()
                    )
                }

            }
            Row(modifier = Modifier.fillMaxWidth().weight(0.2f), horizontalArrangement = Arrangement.End) {
                promptsModal(messages, nutricoachViewModel)
            }
        }


    }

}



@Composable
fun SearchNutricoachScreen(innerPadding: PaddingValues, nutricoachViewModel: NutricoachViewModel, patientViewModel: PatientViewModel,
                     messageViewModel: MessageViewModel) {

    val context = LocalContext.current
    val fruit by nutricoachViewModel.fruit.observeAsState(Fruit("-","-","-","-",Nutrition(0f, 0f, 0f, 0f, 0f)))
    val uiState by nutricoachViewModel.uiState.observeAsState()
    val toast by nutricoachViewModel.toast.observeAsState("")

    LaunchedEffect(toast) {
        if(nutricoachViewModel.checkToast()) {
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
            nutricoachViewModel.clearToast()
        }
    }


    Scaffold(modifier = Modifier.padding(innerPadding)) { padding ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = nutricoachViewModel.fruitName,
                        onValueChange = { nutricoachViewModel.fruitName = it },
                        label = { Text("Fruit Name") },
                        modifier = Modifier.fillMaxWidth(0.65f),
                        maxLines = 1,
                        shape = RoundedCornerShape(100.dp)
                    )


                    Button(
                        modifier = Modifier,
                        onClick = {
                            nutricoachViewModel.searchFruit()
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
                        Text("Details")
                    }


                }

            Spacer(Modifier.height(20.dp))
            FruitDetailsRow("family", fruit.family)
            FruitDetailsRow("calories", fruit.nutritions.calories.toString())
            FruitDetailsRow("fat", fruit.nutritions.fat.toString())
            FruitDetailsRow("sugar", fruit.nutritions.sugar.toString())
            FruitDetailsRow("carbohydrates", fruit.nutritions.carbohydrates.toString())
            FruitDetailsRow("protein", fruit.nutritions.protein.toString())
            Spacer(Modifier.height(20.dp))
            }


        }
    }
}



    @Composable
    fun FruitDetailsRow(label: String = "FRUIT", value: String = "VALUE") {
        Surface(
            modifier = Modifier
        ) {
            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                Spacer(modifier = Modifier.padding(10.dp))
                Text(text = label, modifier = Modifier.weight(1f))
                Text(text = ":", modifier = Modifier.padding(horizontal = 8.dp))
                Text(text = value, modifier = Modifier.weight(2f))
            }
        }
    }


    @Composable
    fun promptsModal(motivationalMessages: List<MotivationalMessage>, nutricoachViewModel: NutricoachViewModel) {


        Button(
            onClick = { nutricoachViewModel.showDialog = true }, shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(5.dp)
        ) {
            Text(text = "Show All Tips")
        }

        if (nutricoachViewModel.showDialog) {
            AlertDialog(
                onDismissRequest = { nutricoachViewModel.showDialog = false },
                title = {
                    Text(
                        text = "AI Tips",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                },
                text = {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(motivationalMessages) { motivationalMessage ->
                            PromptCard(motivationalMessage.message)
                        }
                    }
                },

                confirmButton = {
                    Button(onClick = {
                        nutricoachViewModel.showDialog = false
                    })
                    {
                        Text("Done")
                    }
                }
            )
        }
    }

    @Composable
    fun PromptCard(prompt: String) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Text(text = prompt, fontSize = 20.sp, modifier = Modifier.padding(5.dp))
        }
    }
