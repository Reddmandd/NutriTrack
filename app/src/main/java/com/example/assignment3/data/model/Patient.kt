package com.example.assignment3.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class Patient(
    @PrimaryKey val patientId: Int,

    val patientPhone: String,
    var patientPasswordHash: String ?= null,
    val patientSex: String,
    var name: String ?= null,

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
    var discretionaryFoodScore: Float = 0.0f,

    var fruitServing: Float = 0.0f,
    var fruitVariety: Float = 0.0f,

//    var discretionaryServeSize: Float = 0.0f,
//    var vegetablesWithLegumesAllocatedServeSize: Float = 0.0f,
//    var legumesAllocatedVegetables: Float = 0.0f,
//    var vegetablesVariationsScore: Float = 0.0f,
//    var vegetablesCruciferous: Float = 0.0f,
//    VegetablesTuberandbulb: Float = 0.0f,
//    VegetablesOther: Float = 0.0f,
//    Legumes: Float = 0.0f,
//    VegetablesGreen
//    VegetablesRedandorange
//    FruitHEIFAscoreMale
//    FruitHEIFAscoreFemale
//    Fruitservesize
//    Fruitvariationsscore
//    FruitPome
//    FruitTropicalandsubtropical
//    FruitBerry
//    FruitStone
//    FruitCitrus
//    FruitOther
//    GrainsandcerealsHEIFAscoreMale
//    GrainsandcerealsHEIFAscoreFemale
//    Grainsandcerealsservesize
//    GrainsandcerealsNonwholegrains
//    WholegrainsHEIFAscoreMale
//    WholegrainsHEIFAscoreFemale
//    Wholegrainsservesize
//    MeatandalternativesHEIFAscoreMale
//    MeatandalternativesHEIFAscoreFemale
//    Meatandalternativeswithlegumesallocatedservesize
//    LegumesallocatedMeatandalternatives
//    DairyandalternativesHEIFAscoreMale
//    DairyandalternativesHEIFAscoreFemale
//    Dairyandalternativesservesize
//    SodiumHEIFAscoreMale
//    SodiumHEIFAscoreFemale
//    Sodiummgmilligrams
//    AlcoholHEIFAscoreMale
//    AlcoholHEIFAscoreFemale
//    Alcoholstandarddrinks
//    WaterHEIFAscoreMale
//    WaterHEIFAscoreFemale
//    Water
//    WaterTotalmL
//    BeverageTotalmL
//    SugarHEIFAscoreMale
//    SugarHEIFAscoreFemale
//    Sugar
//    SaturatedFatHEIFAscoreMale
//    SaturatedFatHEIFAscoreFemale
//    SaturatedFat
//    UnsaturatedFatHEIFAscoreMale
//    UnsaturatedFatHEIFAscoreFemale
//    UnsaturatedFatservesize



)