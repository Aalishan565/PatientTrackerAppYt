package com.patienttrackerappyt.presentation.patient_list.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patienttrackerappyt.domain.model.entities.Patient
import com.patienttrackerappyt.domain.repository.PatientRepository
import com.patienttrackerappyt.presentation.patient_list.ui.PatientListUiStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientListViewModel @Inject constructor(private val patientRepository: PatientRepository) :
    ViewModel() {
    private var _patientListUIStateFlow =
        MutableStateFlow<PatientListUiStates<List<Patient>>>(PatientListUiStates.Loading)
    val patientListUIStateFlow: StateFlow<PatientListUiStates<List<Patient>>> get() = _patientListUIStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            patientRepository.getAllPatient().collect {
                _patientListUIStateFlow.value = PatientListUiStates.Success(it)
            }
        }
    }

    fun deletePatientFromDb(patient: Patient) {
        viewModelScope.launch(Dispatchers.IO) {
            patientRepository.deletePatient(patient)
        }
    }

}