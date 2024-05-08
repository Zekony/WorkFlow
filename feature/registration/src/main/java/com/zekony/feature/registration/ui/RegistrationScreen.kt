package com.zekony.feature.registration.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.zekony.feature.registration.mvi.RegistrationEvent
import com.zekony.feature.registration.mvi.RegistrationState
import com.zekony.resources.R
import com.zekony.utility.sharedComposables.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(state: RegistrationState, onEvent: (RegistrationEvent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_emblem),
            contentDescription = null,
            modifier = Modifier
        )
        Text(text = "Create your account", style = MaterialTheme.typography.titleMedium)
        Text(text = "Full name", style = MaterialTheme.typography.bodySmall)
        TextField(
            value = state.nameInput,
            onValueChange = { onEvent(RegistrationEvent.OnNameInput(it)) },
            leadingIcon = { Icon(imageVector = Icons.Sharp.AccountCircle, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = "Email adress", style = MaterialTheme.typography.bodySmall)
        TextField(
            value = state.emailInput,
            onValueChange = { onEvent(RegistrationEvent.OnEmailInput(it)) },
            leadingIcon = { Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = null) },
            )
        Text(text = "Password", style = MaterialTheme.typography.bodySmall)
        TextField(
            value = state.passwordInput,
            onValueChange = { onEvent(RegistrationEvent.OnPasswordInput(it)) },
            leadingIcon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { onEvent(RegistrationEvent.ChangePasswordVisibility) }) {
                    Icon(
                        imageVector = if (state.passwordVisible) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (state.passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )
        PrimaryButton(text = "Sign Up") {
            
        }
    }
}