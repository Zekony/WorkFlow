package com.zekony.feature.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.AirplaneTicket
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.finto.resources.R
import com.zekony.feature.settings.mvi.SettingsEvent
import com.zekony.feature.settings.mvi.SettingsState

@Composable
fun SettingsScreen(state: SettingsState, onEvent: (SettingsEvent) -> Unit) {

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.AirplaneTicket,
                contentDescription = "",
                modifier = Modifier.size(128.dp)
            )
        }
        UserNameRow(state, onEvent)
        LogoutButton { onEvent(SettingsEvent.OnSignOut) }
    }
}

@Composable
fun LogoutButton(signOut: () -> Unit) {
    Button(
        onClick = { signOut() },
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.sign_out_icon),
            contentDescription = stringResource(R.string.sign_out_button),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(stringResource(id = R.string.logout_button), style = MaterialTheme.typography.bodyMedium)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserNameRow(state: SettingsState, onEvent: (SettingsEvent) -> Unit) {
    TextField(
        value = state.currentUser?.name ?: "",
        onValueChange = {  },
        textStyle = MaterialTheme.typography.bodyMedium,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.user_add_icon),
                contentDescription = stringResource(R.string.add_user_icon_description),
                modifier = Modifier.size(16.dp)
            )
        },
        trailingIcon = {
            IconButton(
                onClick = { onEvent(SettingsEvent.ChangeName) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_new_icon),
                    contentDescription = stringResource(R.string.change_name_description),
                    modifier = Modifier.size(16.dp)
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    )
}

