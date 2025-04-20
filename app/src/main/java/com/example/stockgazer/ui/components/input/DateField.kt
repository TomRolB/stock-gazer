package com.example.stockgazer.ui.components.input

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DateField(
    label: String,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    initialDate: LocalDate
) {
    var showPicker by remember { mutableStateOf(false) } // TODO: OK to use remember?
    if (showPicker) {
        DatePickerDialog(onDismissRequest = { showPicker = false }, confirmButton = {
            TextButton(onClick = {
                showPicker = false
                // pull date from state below
            }) { Text("OK") }
        }) {
            DatePicker(
                state = rememberDatePickerState(initialDate.toEpochDay()),
            )
        }
    }

    OutlinedTextField(
        value = initialDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")) ?: "",
        onValueChange = { /* no-op for readOnly */ },
        label = { Text(label) },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showPicker = true }) {
                Icon(Icons.Filled.DateRange, contentDescription = "Select date")
            }
        },
        modifier = modifier
    )
}