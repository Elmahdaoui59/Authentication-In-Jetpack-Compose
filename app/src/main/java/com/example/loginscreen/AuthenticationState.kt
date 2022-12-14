package com.example.loginscreen

import androidx.annotation.StringRes

data class AuthenticationState(
    val email: String? = null,
    val password: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val passwordRequirements: List<PasswordRequirement> = emptyList(),
    val authenticationMode: AuthenticationMode = AuthenticationMode.SIGN_IN
) {
    fun isFormValid(): Boolean? {
        return password?.isNotEmpty() == true
                && email?.isNotEmpty() == true
                && (authenticationMode== AuthenticationMode.SIGN_IN
                || passwordRequirements.containsAll(PasswordRequirement.values().toList()))
    }
}

enum class AuthenticationMode {
    SIGN_IN, SIGN_UP
}

enum class PasswordRequirement(
    @StringRes val label: Int
) {
    CAPITAL_LETTER(R.string.password_requirements_capital),
    NUMBER(R.string.password_requirements_digit),
    EIGHT_CHARACTERS(R.string.password_requirements_characters)
}