package com.patienttrackerappyt.domain.repository

import com.patienttrackerappyt.domain.model.entities.Patient
import kotlinx.coroutines.flow.Flow

interface PatientRepository {

    suspend fun addOrUpdatePatient(patient: Patient)

    suspend fun deletePatient(patient: Patient)

    suspend fun getPatientById(patientId: Int): Patient?

    fun getAllPatient(): Flow<List<Patient>>
}