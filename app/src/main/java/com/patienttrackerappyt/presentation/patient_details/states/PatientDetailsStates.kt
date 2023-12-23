package com.patienttrackerappyt.presentation.patient_details.states

data class PatientDetailsStates(
    val name: String = "",
    val age: String="",
    val gender: Int = 0,
    val doctorAssigned: String = "",
    val prescription: String = ""
)
