package com.patienttrackerappyt.domain.repository

import com.patienttrackerappyt.data.local_db.PatientDao
import com.patienttrackerappyt.domain.model.entities.Patient
import kotlinx.coroutines.flow.Flow

class PatientRepositoryImpl(val patientDao: PatientDao) : PatientRepository {

    override suspend fun addOrUpdatePatient(patient: Patient) {
        patientDao.addOrUpdatePatient(patient = patient)
    }

    override suspend fun deletePatient(patient: Patient) {
        patientDao.deletePatient(patient = patient)
    }

    override suspend fun getPatientById(patientId: Int): Patient? {
       return patientDao.getPatientById(patientId = patientId)
    }

    override fun getAllPatient(): Flow<List<Patient>> {
       return patientDao.getAllPatient()
    }
}