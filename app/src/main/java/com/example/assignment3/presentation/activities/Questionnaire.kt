package com.example.assignment3.presentation.activities

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.assignment3.R
import com.example.assignment3.presentation.activities.ui.theme.Assignment3Theme
import com.example.assignment3.presentation.viewmodels.FoodIntakeViewModel
import com.example.assignment3.presentation.viewmodels.PatientViewModel
import com.example.assignment3.presentation.viewmodels.QuestionaireViewModel

class Questionnaire : ComponentActivity() {

    private val patientViewModel: PatientViewModel by viewModels()
    private val questionnaireViewModel: QuestionaireViewModel by viewModels()
    private val foodIntakeViewModel: FoodIntakeViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment3Theme {
                questionnaireViewModel.getInitialValues(foodIntakeViewModel)
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Questionnaire(innerPadding, questionnaireViewModel,
                        patientViewModel, foodIntakeViewModel)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Questionnaire(paddingValues: PaddingValues, questionaireViewModel: QuestionaireViewModel,
                  patientViewModel: PatientViewModel, foodIntakeViewModel: FoodIntakeViewModel,
                  navController: NavController? = null) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val mContext = LocalContext.current
    val toast by questionaireViewModel.toast.observeAsState("")



    LaunchedEffect(toast) {
        if (questionaireViewModel.hasToast()) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
            questionaireViewModel.clearToast()
        }
        questionaireViewModel.goToNewScreen(mContext, navController)
    }




    Scaffold(
        modifier = Modifier.padding(paddingValues),
        topBar = {
            CenterAlignedTopAppBar(

                title = { Text("Food Intake Questionnaire") },
                navigationIcon = {
                    IconButton(onClick = {
                        questionaireViewModel.logout(mContext)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().verticalScroll(state = rememberScrollState())) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(text = "Tick all the categories you can eat", fontWeight = FontWeight.Bold)
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = questionaireViewModel.rFruits,
                                onCheckedChange = { questionaireViewModel.rFruits = it })
                            Text("Fruits")
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = questionaireViewModel.rRedmeat,
                                onCheckedChange = { questionaireViewModel.rRedmeat = it })
                            Text("Red meat")
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = questionaireViewModel.rFish,
                                onCheckedChange = { questionaireViewModel.rFish = it })
                            Text("Fish")
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = questionaireViewModel.rVegetable,
                                onCheckedChange = { questionaireViewModel.rVegetable = it })
                            Text("Vegetables")
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = questionaireViewModel.rSeafood,
                                onCheckedChange = { questionaireViewModel.rSeafood= it })
                            Text("Seafood")
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = questionaireViewModel.rEggs,
                                onCheckedChange = { questionaireViewModel.rEggs = it })
                            Text("Eggs")
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                    }

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = questionaireViewModel.rGrains,
                                onCheckedChange = { questionaireViewModel.rGrains = it })
                            Text("Grains")
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = questionaireViewModel.rPoultry,
                                onCheckedChange = { questionaireViewModel.rPoultry = it })
                            Text("Poultry")
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = questionaireViewModel.rNutseeds,
                                onCheckedChange = { questionaireViewModel.rNutseeds = it })
                            Text("Nuts/Seeds")
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                    }

                }
                Text(text = "Your Persona", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(
                    text = "People can be classified into three different types based on their " +
                            "eating preferences. Click on each button below to find out the different " +
                            "types, and select the type that best fits you",
                    fontSize = 15.sp, lineHeight = 15.sp
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    modal(
                        name = "Health Devotee",
                        R.drawable.persona_1,
                        "I’m passionate about healthy eating & health plays a big part in my life." +
                                " I use social media to follow active lifestyle personalities or get new " +
                                "recipes/exercise ideas. I may even buy superfoods or follow a particular " +
                                "type of diet. I like to think I am super healthy.",
                        questionaireViewModel.showDialog1,
                        questionaireViewModel.isModalExpanded1
                    )

                    modal(
                        name = "Mindful Eater",
                        R.drawable.persona_2,
                        "I’m health-conscious and being healthy and eating healthy is important to me. Although health means different things to different people, I make conscious lifestyle decisions about eating based on what I believe healthy means. I look for new recipes and healthy eating information on social media.",
                        questionaireViewModel.showDialog2,
                        questionaireViewModel.isModalExpanded2

                    )

                    modal(
                        name = "Wellness Striver",
                        R.drawable.persona_3,
                        "I aspire to be healthy (but struggle sometimes). Healthy eating is hard work! I’ve tried to improve my diet, but always find things that make it difficult to stick with the changes. Sometimes I notice recipe ideas or healthy eating hacks, and if it seems easy enough, I’ll give it a go.",
                        questionaireViewModel.showDialog3,
                        questionaireViewModel.isModalExpanded3
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    modal(
                        name = "Balance Seeker",
                        R.drawable.persona_4,
                        "I try and live a balanced lifestyle, and I think that all foods are okay in moderation. I shouldn’t have to feel guilty about eating a piece of cake now and again. I get all sorts of inspiration from social media like finding out about new restaurants, fun recipes and sometimes healthy eating tips.",
                        questionaireViewModel.showDialog4,
                        questionaireViewModel.isModalExpanded4
                    )

                    modal(
                        name = "Health Procrastinator",
                        R.drawable.persona_5,
                        "I’m contemplating healthy eating but it’s not a priority for me right now. I know the basics about what it means to be healthy, but it doesn’t seem relevant to me right now. I have taken a few steps to be healthier but I am not motivated to make it a high priority because I have too many other things going on in my life.",
                        questionaireViewModel.showDialog5,
                        questionaireViewModel.isModalExpanded5
                    )

                    modal(
                        name = "Food Carefree",
                        R.drawable.persona_6,
                        "I’m not bothered about healthy eating. I don’t really see the point and I don’t think about it. I don’t really notice healthy eating tips or recipes and I don’t care what I eat.",
                        questionaireViewModel.showDialog6,
                        questionaireViewModel.isModalExpanded6
                    )
                }
                Spacer(Modifier.height(20.dp))
                Text(text = "Which persona best fits you?", fontWeight = FontWeight.Bold)

                val personas = arrayOf("Health Devotee", "Mindful Eater", "Wellness Striver", "Balance Seeker",
                    "Health Procrastinator", "Food Carefree")

                ExposedDropdownMenuBox(

                    expanded = questionaireViewModel.isExpanded,
                    onExpandedChange = { questionaireViewModel.isExpanded = it }
                ) {
                    TextField(
                        value = questionaireViewModel.rPersona,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text(questionaireViewModel.rPersona) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = questionaireViewModel.isExpanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = questionaireViewModel.isExpanded,
                        onDismissRequest = { questionaireViewModel.isExpanded = false }
                    ) {
                        personas.forEach { currentPersona ->
                            DropdownMenuItem(
                                text = { Text(currentPersona) },
                                onClick = {
                                    questionaireViewModel.rPersona = currentPersona
                                    questionaireViewModel.isExpanded = false
                                }
                            )

                        }
                    }
                }



                var largestMealDialog = TimePickerFun(questionaireViewModel.rLargestMealTime, questionaireViewModel)
                var sleepingDialog = TimePickerFun(questionaireViewModel.rSleepingTime, questionaireViewModel)
                var wakingDialog = TimePickerFun(questionaireViewModel.rWakingTime, questionaireViewModel)

                Spacer(Modifier.height(20.dp))
                Text(text = "Timings", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Row(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically){

                    Text("What time of day approx. do you \n" +
                            "normally eat your biggest meal?")
                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedButton(
                        onClick = { largestMealDialog.show() }, shape = RoundedCornerShape(10.dp),
                    ) {
                        Text(text = questionaireViewModel.rLargestMealTime.value.toString())
                    }
                }
                Row(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically){

                    Text("What time of day approx. do you \n" +
                            "go to sleep at night?")
                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedButton(
                        onClick = { sleepingDialog.show() }, shape = RoundedCornerShape(10.dp),
                    ) {

                        Text(text = questionaireViewModel.rSleepingTime.value.toString())
                    }
                }
                Row(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically){

                    Text("What time of day approx. do you \n" +
                            "wake up in the morning?")
                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedButton(
                        onClick = { wakingDialog.show() }, shape = RoundedCornerShape(10.dp),
                    ) {

                        Text(text = questionaireViewModel.rWakingTime.value.toString())
                    }
                }
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = {
                            questionaireViewModel.confirmValues(foodIntakeViewModel)
                    }
                    ) {
                        Text("Save")
                    }
                }

                Spacer(modifier = Modifier.size(50.dp))





            }

        }
    }
}



@Composable
fun modal(name: String, resourceId: Int, description: String, showDialog: MutableState<Boolean>, isModalExpanded: MutableState<Boolean>){

    Button(onClick = {showDialog.value = true}, shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(5.dp)){
        Text(text = name)
    }
    if(showDialog.value){
        AlertDialog(
            onDismissRequest = { isModalExpanded.value = false },
            title = { Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally)
            {Image(
                painter = painterResource(id = resourceId),
                contentDescription = "persona image",
                modifier = Modifier.size(150.dp))
            }
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = name, fontWeight = FontWeight.Bold, fontSize = 30.sp,
                        textAlign = TextAlign.Center)

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = description, textAlign = TextAlign.Center)
                }
            },

            confirmButton = {
                Button(onClick = {
                    showDialog.value = false
                })
                {
                    Text("Dismiss")
                }
            }
        )
    }
}


@Composable
fun TimePickerFun(mTime: MutableState<String?>, questionaireViewModel: QuestionaireViewModel): TimePickerDialog{
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()

    val mHour = mCalendar.get(Calendar.HOUR_OF_DAY)
    val mMinute = mCalendar.get(Calendar.MINUTE)

    mCalendar.time = Calendar.getInstance().time
    return TimePickerDialog(
        mContext,
        { _, mHour: Int, mMinute: Int ->
            var (mHourString, mMinuteString) = questionaireViewModel.getTimeString(mHour, mMinute)
            mTime.value = "$mHourString:$mMinuteString"
        }, mHour, mMinute, false
    )
}