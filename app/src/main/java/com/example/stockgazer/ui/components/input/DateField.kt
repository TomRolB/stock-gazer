package com.example.stockgazer.ui.components.input

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.res.stringResource
import com.example.stockgazer.R
import com.example.stockgazer.ui.theme.Primary100
import com.example.stockgazer.ui.theme.Primary500
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DateField(
    label: String,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    initialDate: LocalDate,
    timezone: ZoneId
) {
    var showPicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate
            .atStartOfDay(timezone)
            .toInstant()
            .toEpochMilli()
    )

    if (showPicker) {
        DatePickerDialog(onDismissRequest = { showPicker = false }, confirmButton = {
            TextButton(onClick = {
                showPicker = false
                val selectedDate = datePickerState.selectedDateMillis?.let { millis ->
                        Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
                    } ?: LocalDate.now()

                onDateSelected(selectedDate)

            }) {
                Text(stringResource(R.string.date_picker_confirm_button_text), color = Primary500)
            }
        }) {
            DatePicker(
                state = datePickerState,
            )
        }
    }

    OutlinedTextField(
        value = initialDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")) ?: "",
        onValueChange = {},
        label = { Text(label, color = Primary100) },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showPicker = true }) {
                Icon(Icons.Filled.DateRange, contentDescription = stringResource(R.string.date_picker_title))
            }
        },
        modifier = modifier,
        textStyle = LocalTextStyle.current.copy(color = Primary100)
    )
}