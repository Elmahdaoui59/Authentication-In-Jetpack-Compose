package com.example.loginscreen


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Authentication() {
    val viewModel: AuthenticationViewModel = viewModel()
    MaterialTheme() {
        AuthenticationContent(
            modifier = Modifier.fillMaxWidth(),
            authenticationState = viewModel.uiState.collectAsState().value,
            handleEvent = viewModel::handleEvent
        )
    }
}

@Preview
@Composable
fun DefaultPreview() {
    Authentication()
}

