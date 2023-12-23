package com.patienttrackerappyt.presentation.patient_list.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patienttrackerappyt.domain.model.entities.Patient

@Composable
fun PatientItem(
    patient: Patient,
    onCardClicked: () -> Unit,
    onDeleteConfirmed: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        DeleteDialog(
            title = "Delete",
            message = "Are you sure you want to delete \"${patient.name}\" from patient list?",
            onDialogDismiss = { showDialog = false }, onConfirmBtn = {
                onDeleteConfirmed()
                showDialog = false
            })
    }
    Card(
        modifier = Modifier.clickable { onCardClicked() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(9f)) {
                Text(
                    text = patient.name, style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ), overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Assigned to :${patient.doctorAssigned}",
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis
                )

            }
            IconButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete ")
            }
        }
    }

}

@Preview
@Composable
fun PatientItemPreview() {
    PatientItem(
        Patient(
            name = "Aalishan",
            gender = 1,
            age = "31",
            doctorAssigned = "Vikas",
            prescription = "",
            patientId = 0
        ), onCardClicked = {},
        onDeleteConfirmed = {}
    )
}