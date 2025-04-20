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
fun DateField(modifier: Modifier = Modifier) {
    val selectedDate = LocalDate.now()
    var showPicker by remember { mutableStateOf(false) }
    // 1️⃣ Launch dialog when true
    if (showPicker) {
        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showPicker = false
                    // pull date from state below
                }) { Text("OK") }
            }
        ) {
            DatePicker(
                state = rememberDatePickerState(selectedDate?.toEpochDay() ?: 0),
            )
        }
    }

    // 2️⃣ The input field
    OutlinedTextField(
        value = selectedDate?.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")) ?: "",
        onValueChange = { /* no-op for readOnly */ },
        label = { Text("Trade Date") },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showPicker = true }) {
                Icon(Icons.Filled.DateRange, contentDescription = "Select date")
            }
        },
        modifier = modifier
    )
}