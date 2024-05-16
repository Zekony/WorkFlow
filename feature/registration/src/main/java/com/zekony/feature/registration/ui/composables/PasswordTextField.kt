package com.zekony.feature.registration.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.zekony.feature.registration.mvi.InputError
import com.zekony.feature.registration.mvi.RegistrationEvent
import com.zekony.feature.registration.mvi.RegistrationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(state: RegistrationState, onEvent: (RegistrationEvent) -> Unit) {
    Text(
        text = "Password",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.inversePrimary
    )
    TextField(
        value = state.passwordInput,
        onValueChange = { onEvent(RegistrationEvent.OnPasswordInput(it)) },
        textStyle = MaterialTheme.typography.bodyMedium,
        isError = state.inputError == InputError.PasswordLength || state.inputError == InputError.PasswordShouldContainSymbols,
        leadingIcon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = null) },
        trailingIcon = {
            IconButton(onClick = { onEvent(RegistrationEvent.ChangePasswordVisibility) }) {
                Icon(
                    imageVector = if (state.passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = null
                )
            }
        },
        visualTransformation = if (state.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    )
}