package com.patienttrackerappyt.presentation.patient_details.states

sealed class PatientDetailsActions {
    data class EnteredName(val name: String): PatientDetailsActions()
    data class EnteredAge(val age: String): PatientDetailsActions()
    data class EnteredAssignedDoctor(val doctor: String): PatientDetailsActions()
    data class EnteredPrescription(val prescription: String): PatientDetailsActions()
    object  SelectedMale: PatientDetailsActions()
    object  SelectedFemale: PatientDetailsActions()
    object  SaveButtonClicked: PatientDetailsActions()
}