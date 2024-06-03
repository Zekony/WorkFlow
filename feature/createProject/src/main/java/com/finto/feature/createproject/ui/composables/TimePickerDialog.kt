package com.finto.feature.createproject.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.finto.feature.createproject.mvi.ProjectEvent
import com.finto.feature.createproject.utility.increaseList

@Composable
fun TimePickerDialog(
    title: String,
    onEvent: (ProjectEvent) -> Unit
) {
    val minutesList = (1..60).toMutableList().filter { it % 5 == 0 }.increaseList()
    val hoursList = (1..24).toMutableList().increaseList()

    val minutesLazyListState = rememberLazyListState(minutesList.size / 2)
    val minutesStateValue by calculateStateIndex(minutesList, minutesLazyListState)

    val hoursLazyListState = rememberLazyListState(hoursList.size / 2)
    val hoursStateValue by calculateStateIndex(hoursList, hoursLazyListState)

    Dialog(
        onDismissRequest = { onEvent(ProjectEvent.ShowTimePickerDialog) }
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier,
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                NumbersLazyColumn(
                    hoursList,
                    hoursLazyListState,
                    hoursStateValue
                )
                NumbersLazyColumn(
                    minutesList,
                    minutesLazyListState,
                    minutesStateValue
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = {
                    onEvent(ProjectEvent.PickerTime(hoursStateValue * 3600 + minutesStateValue * 60))
                }) {
                    Text(text = "OK", style = MaterialTheme.typography.titleMedium)
                }
                TextButton(onClick = { onEvent(ProjectEvent.ShowTimePickerDialog) }) {
                    Text(text = "Cancel", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

@Composable
private fun calculateStateIndex(list: List<Int>, lazyListState: LazyListState): State<Int> =
    derivedStateOf {
        (lazyListState.firstVisibleItemIndex.inc() * list.min() % list.max()).run {
            plus(
                if (this != list.max() - list.min()) {
                    list.min()
                } else {
                    list.min() - list.max()
                }
            )
        }
    }


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NumbersLazyColumn(
    list: List<Int>,
    lazyListState: LazyListState,
    stateValue: Int
) {
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)

    val size = 70.dp

    LazyColumn(
        modifier = Modifier.height(size * 3),
        state = lazyListState,
        flingBehavior = flingBehavior
    ) {
        items(list) {
            val alpha = when (it % list.max() - stateValue) {
                0 -> 1.0f
                5, -5 -> 0.5f
                else -> 0.3f
            }
            Box(
                modifier = Modifier.height(size),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (it % list.max()).toString(),
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White.copy(alpha = alpha),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                )
            }
        }
    }
}