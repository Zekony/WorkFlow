package com.zekony.feature.registration.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zekony.feature.registration.mvi.InputError
import com.zekony.feature.registration.mvi.RegistrationEvent
import com.zekony.feature.registration.mvi.RegistrationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameTextField(state: RegistrationState, onEvent: (RegistrationEvent) -> Unit) {
    Text(
        text = "Full name",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.inversePrimary
    )
    TextField(
        value = state.nameInput,
        onValueChange = { onEvent(RegistrationEvent.OnNameInput(it)) },
        textStyle = MaterialTheme.typography.bodyMedium,
        isError = state.inputError == InputError.NameLength,
        leadingIcon = {
            Icon(
                imageVector = Icons.Sharp.AccountCircle,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    )
}