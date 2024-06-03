package com.finto.feature.registration.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.finto.feature.registration.mvi.RegistrationEvent
import com.finto.feature.registration.mvi.RegistrationState
import com.finto.feature.registration.ui.composables.EmailTextField
import com.finto.feature.registration.ui.composables.GoogleButton
import com.finto.feature.registration.ui.composables.NameTextField
import com.finto.feature.registration.ui.composables.PasswordTextField
import com.finto.feature.registration.ui.composables.TermsCheck
import com.finto.resources.R
import com.finto.utility.sharedComposables.PrimaryButton

@Composable
fun RegistrationScreen(state: RegistrationState, onEvent: (RegistrationEvent) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_emblem),
            contentDescription = null,
            modifier = Modifier
                .size(92.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(text = "Create your account", style = MaterialTheme.typography.bodyLarge)
        NameTextField(state, onEvent)
        EmailTextField(state, onEvent)
        PasswordTextField(state, onEvent)
        TermsCheck(state, onEvent)
        PrimaryButton(text = "Sign Up", enabled = state.termsAgreed) {
            onEvent(RegistrationEvent.OnRegistration)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Divider(
                modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.inversePrimary
            )
            Text(
                text = "Or continue with",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier.padding(horizontal = 6.dp)
            )
            Divider(
                modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.inversePrimary
            )
        }
        GoogleButton(onEvent)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already have an account? ",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.inversePrimary
            )
            Text(
                text = "Log in",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onEvent(RegistrationEvent.ToSigningScreen) }
            )
        }
    }
}