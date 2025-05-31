package com.example.stockgazer.ui.screens.shared

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.stockgazer.R
import com.example.stockgazer.security.BiometricAuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BiometricViewModel @Inject constructor(
    private val biometricAuthManager: BiometricAuthManager,
) : ViewModel() {
    private var _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    fun authenticate(context: Context) {
        biometricAuthManager.authenticate(
            context,
            onError = {
                _isAuthenticated.value = false
                Toast.makeText(context,
                    context.getString(R.string.auth_error_message), Toast.LENGTH_SHORT).show()
            },
            onSuccess = {
                _isAuthenticated.value = true
            },
            onFail = {
                _isAuthenticated.value = false
                Toast.makeText(context,
                    context.getString(R.string.auth_fail_message), Toast.LENGTH_SHORT).show()
            }
        )
    }
}