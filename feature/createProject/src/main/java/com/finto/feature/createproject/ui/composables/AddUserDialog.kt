package com.finto.feature.createproject.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.finto.feature.createproject.mvi.ProjectEvent
import com.finto.feature.createproject.mvi.ProjectState
import com.finto.utility.sharedComposables.PrimaryButton

@Composable
fun AddUserDialog(state: ProjectState, onEvent: (ProjectEvent) -> Unit) {
    Dialog(onDismissRequest = { onEvent(ProjectEvent.OpenAddUserDialog) }) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Possible members")
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(state.possibleUsersList) { user ->
                    val userIsChosen = state.chosenUsersIdList.contains(user.id)
                    Row(
                        modifier = Modifier
                            .background(if (userIsChosen) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface, RoundedCornerShape(6.dp))
                            .padding(vertical = 6.dp, horizontal = 12.dp)
                            .clickable { onEvent(ProjectEvent.AddUserToChosen(user.id)) }
                    ) {
                        Text(text = user.name, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
            PrimaryButton(
                text = "Add Team Member",
                enabled = state.chosenUsersIdList.isNotEmpty()
            ) {
                onEvent(ProjectEvent.SaveChosenUsers)
            }
        }
    }
}