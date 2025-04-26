package com.example.stockgazer.ui.components.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.example.stockgazer.R
import com.example.stockgazer.ui.theme.Primary500
import com.example.stockgazer.ui.theme.TimePickerBottomPadding
import com.example.stockgazer.ui.theme.TimePickerButtonsBottomPadding
import com.example.stockgazer.ui.theme.TimePickerButtonsEndPadding
import com.example.stockgazer.ui.theme.TimePickerButtonsSpacing
import com.example.stockgazer.ui.theme.TimePickerButtonsStartPadding
import com.example.stockgazer.ui.theme.TimePickerHorizontalPadding
import com.example.stockgazer.ui.theme.TimePickerTonalElevation
import com.example.stockgazer.ui.theme.TimePickerTopPadding
import java.time.LocalTime

// Code copied from Google's issue tracker:
// https://issuetracker.google.com/issues/288311426?pli=1
//
// This is the official recommendation, since TimePickerDialog
// does not exist in the current version of the material3 library.


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
    initial: LocalTime? = null,
) {
    var mode: DisplayMode by remember { mutableStateOf(DisplayMode.Picker) }
    val state: TimePickerState = rememberTimePickerState(
        initialHour = initial?.hour ?: 0,
        initialMinute = initial?.minute ?: 0,
    )

    fun onConfirmClicked() {
        val localTime = LocalTime.of(state.hour, state.minute)
        onConfirm(localTime)
    }

    // TimePicker does not provide a default TimePickerDialog, so we use our own PickerDialog:
    // https://issuetracker.google.com/issues/288311426
    PickerDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(R.string.time_picker_title)) },
        buttons = {
            DisplayModeToggleButton(
                displayMode = mode,
                onDisplayModeChange = { mode = it },
            )
            Spacer(Modifier.weight(1f))
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.common_button_cancel), color = Primary500)
            }
            TextButton(onClick = ::onConfirmClicked) {
                Text(stringResource(R.string.common_button_ok))
            }
        },
    ) {
        val contentModifier = Modifier.padding(horizontal = TimePickerHorizontalPadding)
        when (mode) {
            DisplayMode.Picker -> TimePicker(modifier = contentModifier, state = state)
            DisplayMode.Input -> TimeInput(modifier = contentModifier, state = state)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DisplayModeToggleButton(
    displayMode: DisplayMode,
    onDisplayModeChange: (DisplayMode) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (displayMode) {
        DisplayMode.Picker -> IconButton(
            modifier = modifier,
            onClick = { onDisplayModeChange(DisplayMode.Input) },
        ) {
            Icon(
                painter = painterResource(R.drawable.keyboard),
                contentDescription = stringResource(R.string.time_picker_button_select_input_mode),
            )
        }

        DisplayMode.Input -> IconButton(
            modifier = modifier,
            onClick = { onDisplayModeChange(DisplayMode.Picker) },
        ) {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = stringResource(R.string.time_picker_button_select_picker_mode),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickerDialog(
    onDismissRequest: () -> Unit,
    title: @Composable () -> Unit,
    buttons: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    BasicAlertDialog(
        modifier = modifier
            .width(IntrinsicSize.Min)
            .height(IntrinsicSize.Min),
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = TimePickerTonalElevation,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Title
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
                    ProvideTextStyle(MaterialTheme.typography.labelLarge) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(horizontal = TimePickerHorizontalPadding)
                                .padding(
                                    top = TimePickerTopPadding,
                                    bottom = TimePickerBottomPadding
                                ),
                        ) {
                            title()
                        }
                    }
                }
                // Content
                CompositionLocalProvider(LocalContentColor provides AlertDialogDefaults.textContentColor) {
                    content()
                }
                // Buttons
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.primary) {
                    ProvideTextStyle(MaterialTheme.typography.labelLarge) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    bottom = TimePickerButtonsBottomPadding,
                                    end = TimePickerButtonsEndPadding,
                                    start = TimePickerButtonsStartPadding
                                ),
                            horizontalArrangement = Arrangement.spacedBy(TimePickerButtonsSpacing, Alignment.End),
                        ) {
                            buttons()
                        }
                    }
                }
            }
        }
    }
}