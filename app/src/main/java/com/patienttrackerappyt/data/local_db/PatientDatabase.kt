package com.patienttrackerappyt.data.local_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.patienttrackerappyt.domain.model.entities.Patient

@Database(entities = [Patient::class], version = 1, exportSchema = false)
abstract class PatientDatabase : RoomDatabase() {

    abstract val patientDao: PatientDao
}