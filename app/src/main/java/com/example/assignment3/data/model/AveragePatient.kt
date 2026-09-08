package com.example.assignment3.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class AveragePatient(
    var patientSex: String,
    var totalScore: Float = 0.0f,
    var vegetableScore: Float = 0.0f,
    var fruitScore: Float = 0.0f,
    var grainScore: Float = 0.0f,
    var wholeGrainScore: Float = 0.0f,
    var meatScore: Float = 0.0f,
    var dairyScore: Float = 0.0f,
    var waterScore: Float = 0.0f,
    var saturatedFatScore: Float = 0.0f,
    var unsaturatedFatScore: Float = 0.0f,
    var sodiumScore: Float = 0.0f,
    var sugarScore: Float = 0.0f,
    var alcoholScore: Float = 0.0f,
    var discretionaryFoodScore: Float = 0.0f


)