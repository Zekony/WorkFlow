package com.finto.feature.createproject.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.finto.feature.createproject.mvi.ProjectEvent
import com.finto.feature.createproject.mvi.ProjectState

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DescriptionTextField(
    description: String,
    onEvent: (ProjectEvent) -> Unit
) {
    Text(text = "Project Description", style = MaterialTheme.typography.titleMedium)
    TextField(
        value = description,
        onValueChange = { onEvent(ProjectEvent.OnDescriptionInput(it)) },
        textStyle = MaterialTheme.typography.bodySmall,
        colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    )
}