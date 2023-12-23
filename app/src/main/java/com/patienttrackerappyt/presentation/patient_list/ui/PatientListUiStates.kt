package com.patienttrackerappyt.presentation.patient_list.ui

sealed class PatientListUiStates<out T> {
    object Idle : PatientListUiStates<Nothing>()
    object Loading : PatientListUiStates<Nothing>()
    data class Success<T>(val data: T) : PatientListUiStates<T>()
    class Error(val message: String) : PatientListUiStates<Nothing>()
}