package com.patienttrackerappyt.presentation.patient_list.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patienttrackerappyt.domain.model.entities.Patient
import com.patienttrackerappyt.presentation.patient_list.view_model.PatientListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListScreen(
    onFabClicked: () -> Unit,
    onItemClicked: (Int?) -> Unit,
) {
    val patientListViewModel: PatientListViewModel = hiltViewModel()
    val patientListScreenUiState by patientListViewModel.patientListUIStateFlow.collectAsState()
    Scaffold(
        topBar = { ListAppBar() },
        floatingActionButton = {
            if (patientListScreenUiState is PatientListUiStates.Success) {
                ListFab(onFabClicked = { onFabClicked() })
            }
        }
    ) { paddingValues ->
        when (patientListScreenUiState) {
            is PatientListUiStates.Success -> {
                HandleSuccessState(
                    paddingValues = paddingValues,
                    patientList = (patientListScreenUiState as PatientListUiStates.Success<List<Patient>>).data,
                    onItemClicked = onItemClicked,
                    onDeleteClicked = {
                        patientListViewModel.deletePatientFromDb(it)
                    }
                )
            }

            is PatientListUiStates.Error -> {}
            PatientListUiStates.Idle -> {}
            PatientListUiStates.Loading -> {
                HandleLoadingState(paddingValues = paddingValues)
            }
        }

    }
}

@Composable
fun HandleLoadingState(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun HandleSuccessState(
    paddingValues: PaddingValues,
    patientList: List<Patient>,
    onItemClicked: (Int?) -> Unit,
    onDeleteClicked: (Patient) -> Unit
) {
    if (patientList.isEmpty()) {
        EmptyListHomeScreen(paddingValues)
    } else {
        FilledListHome(paddingValues, patientList, onItemClicked, onDeleteClicked)
    }
}

@Composable
private fun FilledListHome(
    paddingValues: PaddingValues,
    patientList: List<Patient>,
    onItemClicked: (Int?) -> Unit,
    onDeleteClicked: (Patient) -> Unit
) {
    LazyColumn(
        contentPadding = paddingValues,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(count = patientList.size) { index ->
            PatientItem(
                patient = patientList[index],
                onCardClicked = { onItemClicked(patientList[index].patientId) },
                onDeleteConfirmed = {
                    onDeleteClicked(patientList[index])
                })
        }
    }
}

@Composable
private fun EmptyListHomeScreen(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Add patient by pressing plus button",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListAppBar() {
    TopAppBar(title = {
        Text(
            text = "Patient Tracker",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
        )
    })
}

@Composable
fun ListFab(onFabClicked: () -> Unit) {

    FloatingActionButton(onClick = { onFabClicked() }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add btn")

    }
}

@Preview(showBackground = true)
@Composable
fun PatientListScreenPreview() {
    PatientListScreen(onFabClicked = {}, onItemClicked = {})
}

