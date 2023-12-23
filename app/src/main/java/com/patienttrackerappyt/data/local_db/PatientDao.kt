package com.patienttrackerappyt.data.local_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.patienttrackerappyt.domain.model.entities.Patient
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Upsert
    suspend fun addOrUpdatePatient(patient: Patient)

    @Delete
    suspend fun deletePatient(patient: Patient)

    @Query("SELECT * from patientTable WHERE patientId = :patientId")
    suspend fun getPatientById(patientId: Int): Patient?

    @Query("SELECT * from patientTable")
    fun getAllPatient(): Flow<List<Patient>>
}