package com.patienttrackerappyt.navigation

import com.patienttrackerappyt.navigation.ScreenConstants.PATIENT_DETAILS_SCREEN
import com.patienttrackerappyt.navigation.ScreenConstants.PATIENT_ID_KEY
import com.patienttrackerappyt.navigation.ScreenConstants.PATIENT_LIST_SCREEN

sealed class Screens(val route: String) {
    object PatientList : Screens(PATIENT_LIST_SCREEN)
    object PatientDetails :
        Screens("$PATIENT_DETAILS_SCREEN?${PATIENT_ID_KEY}={${PATIENT_ID_KEY}}") {
        fun passPatientId(patientId: Int?): String {
            return "$PATIENT_DETAILS_SCREEN?${PATIENT_ID_KEY}=${patientId}"
        }
    }
}
