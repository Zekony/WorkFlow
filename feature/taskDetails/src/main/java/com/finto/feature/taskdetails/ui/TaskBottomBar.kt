package com.finto.feature.taskdetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finto.feature.taskdetails.mvi.TaskEvent
import com.finto.utility.sharedComposables.PrimaryButton

@Composable
fun TaskBottomBar(onEvent: (TaskEvent) -> Unit) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(16.dp)
    ) {
        PrimaryButton(text = "Add Task") {
            onEvent(TaskEvent.OpenAddTaskDialog)
        }
    }
}