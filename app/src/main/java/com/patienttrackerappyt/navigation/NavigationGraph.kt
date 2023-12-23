package com.patienttrackerappyt.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.patienttrackerappyt.navigation.ScreenConstants.PATIENT_ID_KEY
import com.patienttrackerappyt.presentation.patient_details.ui.PatientDetailsScreen
import com.patienttrackerappyt.presentation.patient_list.ui.PatientListScreen

@Composable
fun NavigationGraphSetup(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.PatientList.route,
        builder = {
            composable(
                route = Screens.PatientList.route
            ) {
                PatientListScreen(onFabClicked = {
                    navController.navigate(Screens.PatientDetails.route)
                }, onItemClicked = {
                    navController.navigate(Screens.PatientDetails.passPatientId(it))
                })
            }
            composable(
                route = Screens.PatientDetails.route,
                arguments = listOf(navArgument(name = PATIENT_ID_KEY) {
                    type = NavType.IntType
                    defaultValue = -1
                })
            ) {
                PatientDetailsScreen(onBackButtonPressed = {
                    navController.navigateUp()
                }, onSaveSuccess = {
                    navController.navigateUp()
                })
            }
        }
    )
}