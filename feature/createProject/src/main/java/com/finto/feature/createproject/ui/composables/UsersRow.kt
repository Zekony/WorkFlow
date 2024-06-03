package com.finto.feature.createproject.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.finto.domain.home.entities.User
import com.finto.feature.createproject.mvi.ProjectEvent
import com.finto.resources.R


@Composable
@OptIn(ExperimentalLayoutApi::class)
fun UsersRow(
    completedProjectsList: List<User>,
    onEvent: (ProjectEvent) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        completedProjectsList.forEach { user ->
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(6.dp)
                    .widthIn(100.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = user.name, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(R.drawable.delete_team_member),
                    contentDescription = "add team members",
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onEvent(ProjectEvent.RemoveTeamMember(user.id)) }
                )
            }
        }
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(6.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.new_message_icon),
                tint = Color.Black,
                contentDescription = "add team members",
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onEvent(ProjectEvent.OpenAddUserDialog) }
            )
        }
    }
}