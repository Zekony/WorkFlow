package com.finto.feature.taskdetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.finto.feature.taskdetails.mvi.TaskEvent
import com.finto.feature.taskdetails.mvi.TaskState
import com.finto.feature.taskdetails.ui.composables.NewTaskDialog
import com.finto.feature.taskdetails.ui.composables.ProjectInfo
import com.finto.utility.sharedComposables.CustomCircularProgressIndicator

@Composable
fun TaskScreen(state: TaskState, onEvent: (TaskEvent) -> Unit) {

    val scrollState = rememberScrollState()

    if (state.newTaskDialogIsOn) NewTaskDialog(state, onEvent)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = state.project.title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        ProjectInfo(state)

        Text(text = "Project details", style = MaterialTheme.typography.titleMedium)
        Text(text = state.project.description, style = MaterialTheme.typography.labelSmall)

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Project progress", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.weight(1f))
            CustomCircularProgressIndicator(
                modifier = Modifier
                    .size(60.dp)
                    .scale(1.5f),
                currentValue = state.project.projectTasks.filter { it.completed }.size.toFloat() / state.project.projectTasks.size
            )
        }

        Text(text = "All Tasks", style = MaterialTheme.typography.titleMedium)
        state.project.projectTasks.forEach { task ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(12.dp)
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = task.name, style = MaterialTheme.typography.titleMedium)
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { onEvent(TaskEvent.CheckTaskIsDone(task.id))}
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        if (task.completed) Icons.Outlined.CheckCircle else Icons.Outlined.Circle,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}

