package com.example.assignment3.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "food_intakes",
        foreignKeys = [
            ForeignKey(
                entity = Patient::class,
                parentColumns = ["patientId"],
                childColumns = ["patientId"],
                onDelete = ForeignKey.Companion.CASCADE
            )
])
data class FoodIntake (
    @PrimaryKey val patientId: Int,

    var fruits: Boolean = false,
    var vegetable: Boolean = false,
    var grains: Boolean = false,
    var redmeat: Boolean = false,
    var seafood: Boolean = false,
    var poultry: Boolean = false,
    var fish: Boolean = false,
    var eggs: Boolean = false,
    var nutseeds: Boolean = false,
    var persona: String = "Choose your persona",
    var largestMealTime: String = "00:00",
    var sleepingTime: String = "00:00",
    var wakingTime: String = "00:00"
)