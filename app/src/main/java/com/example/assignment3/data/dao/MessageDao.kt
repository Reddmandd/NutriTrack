package com.example.assignment3.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignment3.data.model.MotivationalMessage
import com.example.assignment3.data.model.Patient
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Query("SELECT * FROM motivational_messages WHERE patientId = :patientId ORDER BY id DESC")
    fun getAllMessages(patientId: Int): LiveData<List<MotivationalMessage>>

    @Insert
    suspend fun insertMessages(message: MotivationalMessage)

    @Query("DELETE FROM motivational_messages")
    suspend fun deleteAllPosts()

}