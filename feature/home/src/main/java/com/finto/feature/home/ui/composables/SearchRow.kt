package com.finto.feature.home.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.finto.feature.home.mvi.HomeEvent
import com.finto.resources.R

@Composable
fun SearchRow(modifier: Modifier = Modifier, searchInput: String = "", onEvent: (HomeEvent) -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextField(
            value = searchInput,
            onValueChange = { onEvent(HomeEvent.OnSearchInput(it)) },
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = TextFieldDefaults.colors(focusedContainerColor = MaterialTheme.colorScheme.surface),
            placeholder = {
                Text(
                    text = stringResource(R.string.search_tasks_placeholder),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inversePrimary
                )
            },
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = { onEvent(HomeEvent.NavigateToSettings) },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary, RectangleShape)
                .weight(0.2f)
                .fillMaxHeight(),
        ) {
            Icon(
                painterResource(id = R.drawable.settings_icon),
                contentDescription = stringResource(id = R.string.settings_description),
                modifier = Modifier.size(28.dp)
            )
        }
    }
}