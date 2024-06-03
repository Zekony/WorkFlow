package com.finto.feature.registration.ui.composables

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finto.feature.registration.mvi.InputError
import com.finto.feature.registration.mvi.RegistrationEvent
import com.finto.feature.registration.mvi.RegistrationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(state: RegistrationState, onEvent: (RegistrationEvent) -> Unit) {
    Text(
        text = "Email address",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.inversePrimary
    )
    TextField(
        value = state.emailInput,
        onValueChange = { onEvent(RegistrationEvent.OnEmailInput(it)) },
        textStyle = MaterialTheme.typography.bodyMedium,
        isError = state.inputError == InputError.EmailShouldContain,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    )
}