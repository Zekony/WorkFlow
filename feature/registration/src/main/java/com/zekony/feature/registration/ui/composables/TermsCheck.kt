package com.zekony.feature.registration.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zekony.feature.registration.mvi.RegistrationEvent
import com.zekony.feature.registration.mvi.RegistrationState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TermsCheck(state: RegistrationState, onEvent: (RegistrationEvent) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = state.termsAgreed,
            onCheckedChange = { onEvent(RegistrationEvent.AgreeWithTerms) },
        )
        FlowRow {
            Text(
                text = "I have read & agreed to WorkFlow ",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.inversePrimary
            )
            Text(
                text = "Privacy Policy, ",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { }
            )
            Text(
                text = "Terms & Condition",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { }
            )
        }
    }
}