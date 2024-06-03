package com.finto.utility.sharedComposables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.finto.resources.R.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTitleTopBar(
    title: String,
    navigateBack: () -> Unit,
    @DrawableRes icon: Int? = null,
    action: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.titleMedium) },
        navigationIcon = {
            IconButton(onClick = { navigateBack() }) {
                Icon(
                    painter = painterResource(id = drawable.arrow_left_icon),
                    contentDescription = "navigate back",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        actions = {
            if (icon != null) {
                IconButton(onClick = { action() }) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "screen specific action",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.background)
    )
}