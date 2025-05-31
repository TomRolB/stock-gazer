package com.example.stockgazer.ui.auth

sealed interface BiometricState {}

data object BiometricAvailable : BiometricState

data class BiometricError(val errorTitle: String, val errorMessage: String): BiometricState