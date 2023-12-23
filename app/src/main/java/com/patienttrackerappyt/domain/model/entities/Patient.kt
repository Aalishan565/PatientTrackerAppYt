package com.patienttrackerappyt.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.patienttrackerappyt.utils.Constants.PATIENT_TABLE

@Entity (tableName = PATIENT_TABLE)
data class Patient(
    val name: String,
    val gender: Int,
    val age: String,
    val doctorAssigned: String,
    val prescription: String,
    @PrimaryKey(autoGenerate = true)
    val patientId: Int? = null
)