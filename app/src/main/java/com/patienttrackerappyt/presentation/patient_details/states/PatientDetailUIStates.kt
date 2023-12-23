package com.patienttrackerappyt.presentation.patient_details.states

sealed class PatientDetailUIStates() {
    class Error(val message: String) : PatientDetailUIStates()
    object Success : PatientDetailUIStates()
    object IDLE : PatientDetailUIStates()
}