package com.zekony.feature.registration.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.zekony.feature.registration.mvi.RegistrationEvent
import com.zekony.feature.registration.mvi.RegistrationState

@Composable
fun SigningScreen(state: RegistrationState, onEvent: (RegistrationEvent) -> Unit) {
    Text(text = "Signing")
}