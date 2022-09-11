package com.example.loginscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthenticationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AuthenticationState())
    val uiState = _uiState.asStateFlow()


    private fun toggleAuthenticationMode() {
        val authenticationMode = uiState.value.authenticationMode

        val newAuthenticationMode = if (authenticationMode == AuthenticationMode.SIGN_IN) {
            AuthenticationMode.SIGN_UP
        } else AuthenticationMode.SIGN_IN

        _uiState.update {
            it.copy(
                authenticationMode = newAuthenticationMode
            )
        }
    }

    private fun emailChange(email: String) {
        _uiState.update {
            it.copy(
                email = email
            )
        }
    }

    private fun passwordChange(password: String) {
        val requirements = mutableListOf<PasswordRequirement>()
        if (password.length > 8) {
            requirements.add(PasswordRequirement.EIGHT_CHARACTERS)
        }
        if (password.any() { it.isDigit() }) {
            requirements.add(PasswordRequirement.NUMBER)
        }
        if (password.any() { it.isUpperCase() }) {
            requirements.add(PasswordRequirement.CAPITAL_LETTER)
        }
        _uiState.update {
            it.copy(
                password = password,
                passwordRequirements = requirements
            )
        }
    }

    private fun authenticate() {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
        //trigger network request
        viewModelScope.launch(Dispatchers.IO) {
            delay(3000L)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    error = "Some thing went wrong!"
                )
            }
        }

    }

    private fun dismissError() {
        _uiState.update {
            it.copy(
                error = null
            )
        }
    }

    fun handleEvent(authenticationEvent: AuthenticationEvent) {
        when (authenticationEvent) {
            is AuthenticationEvent.ToggleAuthenticationMode -> {
                toggleAuthenticationMode()
            }

            is AuthenticationEvent.EmailChanged -> {
                emailChange(authenticationEvent.emailAddress)
            }
            is AuthenticationEvent.PasswordChanged -> {
                passwordChange(authenticationEvent.password)
            }
            is AuthenticationEvent.Authenticate -> {
                authenticate()
            }
            is AuthenticationEvent.ErrorDismissed -> {
                dismissError()
            }

        }
    }
}