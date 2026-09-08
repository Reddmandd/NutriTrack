package com.example.assignment3.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "motivational_messages",
    foreignKeys = [
        ForeignKey(
        entity = Patient::class,
        parentColumns = ["patientId"],
        childColumns = ["patientId"],
        onDelete = ForeignKey.Companion.CASCADE
        )
    ])
data class MotivationalMessage (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val patientId: Int,
    var message: String
)