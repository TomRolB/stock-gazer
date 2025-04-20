package com.example.stockgazer.ui.components.input

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeField(
    label: String,
    modifier: Modifier = Modifier,
    initialTime: LocalTime = LocalTime.now(),
    onTimeSelected: (LocalTime) -> Unit
) {
    var showPicker by remember { mutableStateOf(false) } // TODO: OK to use remember?
    var selectedTime by remember { mutableStateOf(initialTime) }

    if (showPicker) {
        TimePickerDialog(
            onDismissRequest = { showPicker = false },
            onConfirm = {  }
        )
    }

    // 4️⃣ The read‑only text field with a clock icon
    OutlinedTextField(
        value       = selectedTime.format(DateTimeFormatter.ofPattern("h:mm a")),
        onValueChange = {},
        readOnly    = true,
        label       = { Text(label) },
        trailingIcon = {
            IconButton(onClick = { showPicker = true }) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Select time")
            }
        },
        modifier    = modifier
    )
}
