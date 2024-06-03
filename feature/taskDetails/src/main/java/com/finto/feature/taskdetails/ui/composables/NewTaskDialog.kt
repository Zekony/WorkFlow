package com.finto.feature.taskdetails.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.finto.feature.taskdetails.mvi.TaskEvent
import com.finto.feature.taskdetails.mvi.TaskState
import com.finto.utility.sharedComposables.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskDialog(state: TaskState, onEvent: (TaskEvent) -> Unit) {
    Dialog(onDismissRequest = { onEvent(TaskEvent.OpenAddTaskDialog) }) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "New task name", style = MaterialTheme.typography.titleMedium)
            TextField(
                value = state.newTaskNameInput,
                onValueChange = { onEvent(TaskEvent.OnNewTaskNameInput(it)) },
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            )
            PrimaryButton(text = "Save new task") {
                onEvent(TaskEvent.SaveNewTask)
            }
        }
    }
}
