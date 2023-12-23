package com.patienttrackerappyt.presentation.patient_list.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DeleteDialog(
    title: String,
    message: String,
    onDialogDismiss: () -> Unit,
    onConfirmBtn: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDialogDismiss() },
        title = { Text(text = title, style = MaterialTheme.typography.bodyLarge) },
        text = { Text(text = message, style = MaterialTheme.typography.bodySmall) },
        confirmButton = {
            TextButton(onClick = { onConfirmBtn() }) {
                Text(text = "YES")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDialogDismiss() }) {
                Text(text = "NO")
            }
        },
    )

}

@Preview
@Composable
fun DeleteDialogPreview() {
    DeleteDialog(
        title = "Title",
        message = "This is message",
        onDialogDismiss = {},
        onConfirmBtn = {})
}