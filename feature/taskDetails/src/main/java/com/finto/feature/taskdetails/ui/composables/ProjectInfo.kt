package com.finto.feature.taskdetails.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.finto.feature.taskdetails.mvi.TaskState
import com.finto.resources.R
import com.finto.utility.functions.toDate

@Composable
fun ProjectInfo(state: TaskState) {
    Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
        Row {
            PrimaryIconButton(icon = R.drawable.due_date_icon)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = "Due Date", style = MaterialTheme.typography.labelSmall)
                Text(
                    text = state.project.dueDateEpochSeconds.toDate(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        Row {
            PrimaryIconButton(icon = R.drawable.project_team_icon)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = "Project Team", style = MaterialTheme.typography.labelSmall)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun PrimaryIconButton(@DrawableRes icon: Int, onClick: (() -> Unit)? = null) {
    val clickable = if (onClick != null) Modifier.clickable { onClick() } else Modifier
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(MaterialTheme.colorScheme.primary)
            .then(clickable),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(28.dp)
        )
    }
}
