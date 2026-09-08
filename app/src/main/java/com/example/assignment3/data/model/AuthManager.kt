package com.example.assignment3.data.model

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit

/**
 *
 * Referenced from Week8's Lab
 */
object AuthManager {
    val _patientId: MutableState<Int?> = mutableStateOf(null)

    fun login(patientId: Int, context: Context) {
        val sharedPref = context.getSharedPreferences("data", MODE_PRIVATE)
        sharedPref.edit() {
            putInt("current_user", patientId)
            apply()
        }
        _patientId.value = patientId
    }

    fun logout(context: Context) {
        val sharedPref = context.getSharedPreferences("data", MODE_PRIVATE)
        sharedPref.edit() {
            putInt("current_user", -1)
            apply()
        }
        _patientId.value = null
    }

    fun getPatientId(): Int? {
        return _patientId.value
    }
}