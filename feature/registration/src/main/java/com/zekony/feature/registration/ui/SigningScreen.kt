package com.zekony.feature.registration.ui

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
import com.zekony.feature.registration.mvi.RegistrationEvent
import com.zekony.feature.registration.mvi.RegistrationState
import com.zekony.feature.registration.ui.composables.EmailTextField
import com.zekony.feature.registration.ui.composables.GoogleButton
import com.zekony.feature.registration.ui.composables.PasswordTextField
import com.zekony.resources.R
import com.zekony.utility.sharedComposables.PrimaryButton

@Composable
fun SigningScreen(state: RegistrationState, onEvent: (RegistrationEvent) -> Unit) {
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
        Text(text = "Welcome back", style = MaterialTheme.typography.bodyLarge)
        EmailTextField(state, onEvent)

        Column {
            PasswordTextField(state, onEvent)
            Text(text = "Forgot password?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        // TODO()
                    })
        }
        PrimaryButton(text = "Log In", enabled = state.termsAgreed) {

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
                text = "Don't have an account? ",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.inversePrimary
            )
            Text(
                text = "Sign up",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onEvent(RegistrationEvent.ToRegistrationScreen) }
            )
        }
    }
}