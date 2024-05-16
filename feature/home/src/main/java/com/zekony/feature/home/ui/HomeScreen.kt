package com.zekony.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.zekony.feature.home.mvi.HomeEvent
import com.zekony.feature.home.mvi.HomeState
import com.zekony.feature.home.ui.composables.SearchRow

@Composable
fun HomeScreen(state: HomeState, onEvent: (HomeEvent) -> Unit) {

    val screenSize =
        DpSize(
            LocalConfiguration.current.screenWidthDp.dp,
            LocalConfiguration.current.screenHeightDp.dp
        )

    val lazyRowState = rememberLazyListState()
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SearchRow(state, onEvent)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Completed tasks", style = MaterialTheme.typography.titleMedium)
            Text(
                text = "See all",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { } // TODO()
            )
        }
        LazyRow(
            state = lazyRowState,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(
                state.completedProjectsList
            ) { index, project ->
                CompositionLocalProvider(LocalContentColor provides if (index == 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface) {
                    Column(
                        modifier = Modifier
                            .background(if (index == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
                            .padding(12.dp)
                            .width((screenSize.width.value / 2.5).toInt().dp)
                            .height(145.dp)
                    ) {
                        Text(
                            text = project.name,
                            style = MaterialTheme.typography.bodyLarge,
                            color = LocalContentColor.current
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Row {
                            Text(text = "Team members", style = MaterialTheme.typography.labelSmall)
                            // TODO()  add members' icons
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Completed", style = MaterialTheme.typography.labelSmall)
                            Text(text = "100%", style = MaterialTheme.typography.labelSmall)
                        }
                        Divider(
                            thickness = 3.dp,
                            color = LocalContentColor.current,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}