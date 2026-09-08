package com.example.assignment3.data.model

data class Fruit(
    val name: String,
    val family: String,
    val genus: String,
    val order: String,
    val nutritions: Nutrition
)

data class Nutrition(
    val carbohydrates: Float,
    val protein: Float,
    val fat: Float,
    val calories: Float,
    val sugar: Float
)