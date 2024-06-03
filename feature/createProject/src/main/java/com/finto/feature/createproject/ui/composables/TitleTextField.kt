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

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TitleTextField(
    title: String,
    onEvent: (ProjectEvent) -> Unit
) {
    Text(text = "Project Title", style = MaterialTheme.typography.titleMedium)
    TextField(
        value = title,
        onValueChange = { onEvent(ProjectEvent.OnTitleInput(it)) },
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    )
}