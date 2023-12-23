package com.patienttrackerappyt.presentation.patient_details.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patienttrackerappyt.domain.model.entities.Patient
import com.patienttrackerappyt.domain.repository.PatientRepository
import com.patienttrackerappyt.navigation.ScreenConstants.PATIENT_ID_KEY
import com.patienttrackerappyt.presentation.patient_details.states.PatientDetailUIStates
import com.patienttrackerappyt.presentation.patient_details.states.PatientDetailsFormValidationException
import com.patienttrackerappyt.presentation.patient_details.states.PatientDetailsActions
import com.patienttrackerappyt.presentation.patient_details.states.PatientDetailsStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientDetailsViewModel @Inject constructor(
    private val patientRepository: PatientRepository,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    var patientDetailsUIStates by mutableStateOf(PatientDetailsStates())
    private var currentPatientId: Int? = -1

    private var _patientDetailsUIEvents =
        MutableStateFlow<PatientDetailUIStates>(PatientDetailUIStates.IDLE)

    val patientDetailsUIEvents: StateFlow<PatientDetailUIStates> get() = _patientDetailsUIEvents.asStateFlow()

    init {
        fetchPatientDetails()
    }

    fun onActionPerformed(events: PatientDetailsActions) {
        when (events) {
            is PatientDetailsActions.EnteredAge -> {
                patientDetailsUIStates = patientDetailsUIStates.copy(age = events.age)
            }

            is PatientDetailsActions.EnteredAssignedDoctor -> {
                patientDetailsUIStates = patientDetailsUIStates.copy(doctorAssigned = events.doctor)
            }

            is PatientDetailsActions.EnteredName -> {
                patientDetailsUIStates = patientDetailsUIStates.copy(name = events.name)
            }

            is PatientDetailsActions.EnteredPrescription -> {
                patientDetailsUIStates =
                    patientDetailsUIStates.copy(prescription = events.prescription)
            }

            PatientDetailsActions.SaveButtonClicked -> {
                savePatient()
            }

            PatientDetailsActions.SelectedFemale -> {
                patientDetailsUIStates = patientDetailsUIStates.copy(gender = 2)
            }

            PatientDetailsActions.SelectedMale -> {
                patientDetailsUIStates = patientDetailsUIStates.copy(gender = 1)
            }
        }
    }

    private fun savePatient() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                performValidation()
                patientRepository.addOrUpdatePatient(
                    patient = Patient(
                        name = patientDetailsUIStates.name.trim(),
                        gender = patientDetailsUIStates.gender,
                        age = patientDetailsUIStates.age,
                        doctorAssigned = patientDetailsUIStates.doctorAssigned.trim(),
                        prescription = patientDetailsUIStates.prescription.trim(),
                        patientId = if (currentPatientId != -1) {
                            currentPatientId
                        } else {
                            null
                        }
                    )
                )
                _patientDetailsUIEvents.emit(PatientDetailUIStates.Success)
            } catch (e: Exception) {
                _patientDetailsUIEvents.emit(
                    PatientDetailUIStates.Error(
                        e.message ?: "Couldn't save the patient"
                    )
                )
            }

        }
    }

    private fun performValidation() {
        val age = patientDetailsUIStates.age.toIntOrNull()
        when {
            patientDetailsUIStates.name.isEmpty() -> {
                throw PatientDetailsFormValidationException("Please enter name")
            }

            patientDetailsUIStates.age.isEmpty() -> {
                throw PatientDetailsFormValidationException("Please enter age")
            }

            patientDetailsUIStates.doctorAssigned.isEmpty() -> {
                throw PatientDetailsFormValidationException("Please enter doctor assigned")
            }

            patientDetailsUIStates.prescription.isEmpty() -> {
                throw PatientDetailsFormValidationException("Please enter prescription")
            }

            patientDetailsUIStates.gender == 0 -> {
                throw PatientDetailsFormValidationException("Please check gender")
            }

            (age == null) || (age < 0) || (age > 150) -> {
                throw PatientDetailsFormValidationException("Please enter valid age")
            }
        }
    }

    private fun fetchPatientDetails() {
        viewModelScope.launch {
            savedStateHandle.get<Int>(key = PATIENT_ID_KEY)?.let { patientId ->
                if (patientId != -1) {
                    patientRepository.getPatientById(patientId)?.apply {
                        patientDetailsUIStates = patientDetailsUIStates.copy(
                            name = name,
                            age = age,
                            gender = gender,
                            doctorAssigned = doctorAssigned,
                            prescription = prescription
                        )
                        currentPatientId = patientId
                    }

                }

            }
        }
    }
}


