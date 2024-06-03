package com.finto.feature.home.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.finto.feature.home.mvi.HomeEvent
import com.finto.feature.home.mvi.HomeState
import com.finto.resources.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRow(state: HomeState, onEvent: (HomeEvent) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextField(
            value = state.searchInput,
            onValueChange = { onEvent(HomeEvent.OnSearchInput(it)) },
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
            placeholder = {
                Text(
                    text = "Search tasks",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inversePrimary
                )
            },
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = { },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary, RectangleShape)
                .weight(0.2f)
                .fillMaxHeight(),
        ) {
            Icon(
                painterResource(id = R.drawable.settings_icon),
                contentDescription = "settings",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}