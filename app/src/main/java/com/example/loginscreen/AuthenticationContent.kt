package com.example.loginscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun AuthenticationContent(
    modifier: Modifier = Modifier,
    authenticationState: AuthenticationState,
    handleEvent: (event: AuthenticationEvent) -> Unit
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (authenticationState.isLoading) {
            CircularProgressIndicator()
        } else {
            AuthenticationForm(
                authenticationMode = authenticationState.authenticationMode,
                email = authenticationState.email,
                password = authenticationState.password,
                onEmailChanged = {
                    handleEvent(AuthenticationEvent.EmailChanged(it))
                },
                onPasswordChanged = {
                    handleEvent(
                        AuthenticationEvent.PasswordChanged(
                            it
                        )
                    )
                },
                onToggleAthMode = { handleEvent(AuthenticationEvent.ToggleAuthenticationMode) },
                passwordSatisfiedRequirement = authenticationState.passwordRequirements,
                onAuthenticate = { handleEvent(AuthenticationEvent.Authenticate) },
                enableAuthentication = authenticationState.isFormValid()
            )
            authenticationState.error?.let {
                AuthenticationErrorDialog(error = it) {
                    handleEvent(AuthenticationEvent.ErrorDismissed)
                }
            }
        }
    }
}
