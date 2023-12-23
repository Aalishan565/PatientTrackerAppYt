package com.patienttrackerappyt.presentation.patient_details.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patienttrackerappyt.presentation.patient_details.states.PatientDetailUIStates
import com.patienttrackerappyt.presentation.patient_details.states.PatientDetailsActions
import com.patienttrackerappyt.presentation.patient_details.view_model.PatientDetailsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientDetailsScreen(
    onBackButtonPressed: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    val patientDetailsViewModel: PatientDetailsViewModel = hiltViewModel()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val patientState = patientDetailsViewModel.patientDetailsUIStates
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit, block = {
        delay(100)
        focusRequester.requestFocus()
    })

    LaunchedEffect(key1 = Unit, block = {
        patientDetailsViewModel.patientDetailsUIEvents.collectLatest { uiState ->
            when (uiState) {
                is PatientDetailUIStates.Error -> {
                    Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
                }

                PatientDetailUIStates.IDLE -> {}
                PatientDetailUIStates.Success -> {
                    onSaveSuccess()
                    Toast.makeText(context, "Patient saved successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }
    })
    Scaffold(topBar = { PatientDetailsTopAppBar(onBackButtonPressed = { onBackButtonPressed() }) }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues = paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                value = patientState.name,
                onValueChange = { newName ->
                    patientDetailsViewModel.onActionPerformed(
                        PatientDetailsActions.EnteredName(
                            newName
                        )
                    )
                },
                label = { Text(text = "Name") },
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = patientState.age,
                    onValueChange = { age ->
                        patientDetailsViewModel.onActionPerformed(
                            PatientDetailsActions.EnteredAge(
                                age
                            )
                        )
                    },
                    label = { Text(text = "Age") },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(
                            FocusDirection.Next
                        )
                    })
                )
                RadioGroupButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = "Male",
                    selected = patientState.gender == 1,
                    onClicked = { patientDetailsViewModel.onActionPerformed(PatientDetailsActions.SelectedMale) })
                RadioGroupButton(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = "Female",
                    selected = patientState.gender == 2,
                    onClicked = { patientDetailsViewModel.onActionPerformed(PatientDetailsActions.SelectedFemale) })
            }
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = patientState.doctorAssigned,
                onValueChange = { doctor ->
                    patientDetailsViewModel.onActionPerformed(
                        PatientDetailsActions.EnteredAssignedDoctor(
                            doctor
                        )
                    )
                },
                label = { Text(text = "Assigned Doctor's name") },
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(
                        FocusDirection.Next
                    )
                })
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = patientState.prescription,
                onValueChange = { prescription ->
                    patientDetailsViewModel.onActionPerformed(
                        PatientDetailsActions.EnteredPrescription(
                            prescription
                        )
                    )
                },
                label = { Text(text = "Prescription") },
                textStyle = MaterialTheme.typography.bodyMedium,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {

                })
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    patientDetailsViewModel.onActionPerformed(PatientDetailsActions.SaveButtonClicked)
                }) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )

            }
            Spacer(modifier = Modifier.height(12.dp))
        }

    }

}

@Composable
fun RadioGroupButton(
    modifier: Modifier,
    text: String,
    selected: Boolean,
    onClicked: () -> Unit
) {
    Row(
        modifier = modifier.clickable { onClicked() },
        verticalAlignment = Alignment.CenterVertically
    ) {

    }
    RadioButton(
        selected = selected,
        onClick = { onClicked() },
        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
    )
    Text(
        text = text, style = MaterialTheme.typography.bodyMedium
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientDetailsTopAppBar(onBackButtonPressed: () -> Unit) {
    TopAppBar(title = {
        Text(
            text = "Patient Detail",
            style = MaterialTheme.typography.headlineLarge
        )
    }, navigationIcon = {
        IconButton(onClick = { onBackButtonPressed() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")

        }
    })

}

@Preview
@Composable
fun PatientDetailsScreenPreview() {
    PatientDetailsScreen(onBackButtonPressed = {}, onSaveSuccess = {})
}