package com.example.stockgazer.ui.components.input

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.stockgazer.R
import com.example.stockgazer.ui.theme.IconMedium
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TimeField(
    label: String,
    modifier: Modifier = Modifier,
    initialTime: LocalTime = LocalTime.now(),
    onTimeSelected: (LocalTime) -> Unit
) {
    var showPicker by remember { mutableStateOf(false) }

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
        label = { Text(label, color = MaterialTheme.colorScheme.primary) },
        trailingIcon = {
            IconButton(onClick = { showPicker = true }) {
                Icon(
                    painter = painterResource(R.drawable.clock),
                    contentDescription = "Select time",
                    modifier = Modifier.size(IconMedium)
                )
            }
        },
        modifier = modifier,
        textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.primary)
    )
}
