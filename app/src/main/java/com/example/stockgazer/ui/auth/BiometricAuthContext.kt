package com.example.stockgazer.ui.auth

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.stockgazer.R
import com.example.stockgazer.ui.screens.shared.BiometricViewModel

@Composable
fun BiometricAuthContext(
    content: @Composable (biometricState: BiometricState) -> Unit
) {
    val context = LocalContext.current

    val viewModel = hiltViewModel<BiometricViewModel>()
    val isAuthenticated by viewModel.isAuthenticated.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.authenticate(context)
    }

    val biometricManager = remember { BiometricManager.from(context) }

    val isBiometricAvailable = remember {
        biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
    }

    when (isBiometricAvailable) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            // Biometric features are available
            if (isAuthenticated) {
                content(BiometricAvailable)
            } else {
                // User needs to authenticate
                content(
                    BiometricError(
                        stringResource(R.string.biometric_success_title),
                        stringResource(R.string.biometric_success_description)
                    )
                )
            }
        }

        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
            // No biometric features available on this device
            content(
                BiometricError(
                    stringResource(R.string.biometric_error_no_hardware_title),
                    stringResource(R.string.biometric_error_no_hardware_description)
                )
            )
        }

        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
            // Biometric features are currently unavailable.
            content(
                BiometricError(
                    stringResource(R.string.biometric_error_hardware_unavailable_title),
                    stringResource(R.string.biometric_error_hardware_unavailable_description)
                )
            )
        }

        BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
            // Biometric features available but a security vulnerability has been discovered
            content(
                BiometricError(
                    stringResource(R.string.biometric_error_security_update_required_title),
                    stringResource(R.string.biometric_error_security_update_required_description)
                )
            )
        }

        BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
            // Biometric features are currently unavailable because the specified options are incompatible with the current Android version..
            content(
                BiometricError(
                    stringResource(R.string.biometric_error_unsupported_title),
                    stringResource(R.string.biometric_error_unsupported_description)
                )
            )
        }

        BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
            // Unable to determine whether the user can authenticate using biometrics
            content(
                BiometricError(
                    stringResource(R.string.biometric_status_unknown_title),
                    stringResource(R.string.biometric_status_unknown_description)
                )
            )
        }

        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
            // The user can't authenticate because no biometric or device credential is enrolled.
            content(
                BiometricError(
                    stringResource(R.string.biometric_error_none_enrolled_title),
                    stringResource(R.string.biometric_error_none_enrolled_description)
                )
            )
        }
    }
}