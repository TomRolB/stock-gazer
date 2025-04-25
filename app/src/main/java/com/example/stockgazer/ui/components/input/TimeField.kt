package com.example.stockgazer.ui.components.input

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.stockgazer.ui.theme.Primary100
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TimeField(
    label: String,
    modifier: Modifier = Modifier,
    initialTime: LocalTime = LocalTime.now(),
    onTimeSelected: (LocalTime) -> Unit
) {
    var showPicker by remember { mutableStateOf(false) } // TODO: OK to use remember?

    if (showPicker) {
        TimePickerDialog(
            onDismissRequest = { showPicker = false },
            onConfirm = {
                showPicker = false
                onTimeSelected(it)
            }
        )
    }

    OutlinedTextField(
        value = initialTime.format(DateTimeFormatter.ofPattern("h:mm a")),
        onValueChange = {},
        readOnly = true,
        label = { Text(label, color = Primary100) },
        trailingIcon = {
            IconButton(onClick = { showPicker = true }) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Select time")
            }
        },
        modifier = modifier,
        textStyle = LocalTextStyle.current.copy(color = Primary100)
    )
}
