package com.patienttrackerappyt.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.patienttrackerappyt.navigation.NavigationGraphSetup
import com.patienttrackerappyt.presentation.theme.PatientTrackerAppYtTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            PatientTrackerAppYtTheme {
                NavigationGraphSetup(navController)
            }
        }
    }
}