package com.patienttrackerappyt.di

import android.app.Application
import androidx.room.Room
import com.patienttrackerappyt.data.local_db.PatientDao
import com.patienttrackerappyt.data.local_db.PatientDatabase
import com.patienttrackerappyt.domain.repository.PatientRepository
import com.patienttrackerappyt.domain.repository.PatientRepositoryImpl
import com.patienttrackerappyt.utils.Constants.PATIENT_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePatientDatabase(app: Application): PatientDatabase {
        return Room.databaseBuilder(app, PatientDatabase::class.java, PATIENT_DB).build()
    }

    @Provides
    @Singleton
    fun providePatientDao(patientDb: PatientDatabase): PatientDao {
        return patientDb.patientDao
    }

    @Provides
    @Singleton
    fun providePatientRepository(patientDao: PatientDao): PatientRepository {
        return PatientRepositoryImpl(patientDao)
    }
}