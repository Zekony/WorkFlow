package com.finto.feature.home.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finto.domain.home.entities.Project
import com.finto.feature.home.mvi.HomeEvent
import com.finto.utility.functions.toDate
import com.finto.utility.sharedComposables.CustomCircularProgressIndicator

@Composable
fun ProjectsColumn(projectList: List<Project>, onEvent: (HomeEvent) -> Unit) {
    projectList.forEach { project ->
        val projectProgress =
            (project.projectTasks.filter { it.completed == true }.size.toFloat() / project.projectTasks.size)
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(12.dp)
                .height(IntrinsicSize.Min)
                .clickable { onEvent(HomeEvent.OnProjectClick(project.id)) }
        ) {
            Text(
                text = project.title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
            Row {
                Column {
                    Text(text = "Team members", style = MaterialTheme.typography.labelSmall)
                    // TODO() add members' icons
                    Spacer(modifier = Modifier.height(25.dp))
                    Text(
                        text = "Due on: ${project.dueDateEpochSeconds.toDate()}",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    CustomCircularProgressIndicator(
                        modifier = Modifier
                            .size(50.dp)
                            .scale(1.5f),
                        currentValue = projectProgress,
                    )
                }
            }
        }
    }
}