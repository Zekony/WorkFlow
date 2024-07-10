package com.finto.feature.home.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.finto.resources.R

@Composable
fun ProjectsTitle(
    titleText: String,
    onSeeAllClick: () -> Unit,
    buttonText: String = stringResource(R.string.see_all)
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = titleText,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.testTag(stringResource(R.string.projects_title_test_tag))
        )
        Text(
            text = buttonText,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onSeeAllClick() }
        )
    }
}