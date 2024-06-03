package com.finto.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.finto.feature.createproject.CREATE_PROJECT_ROUTE

@Composable
fun AppBottomBar(navController: NavHostController) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationButtons.values().forEach {
                val currentDestination =
                    navController.currentBackStackEntryAsState().value?.destination?.route
                if (it != NavigationButtons.CreateNewTask) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { navController.navigate(it.route) },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = if (currentDestination == it.route) it.activeIcon else it.notActiveIcon),
                            contentDescription = stringResource(id = it.screenName),
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = stringResource(id = it.screenName),
                            style = MaterialTheme.typography.bodySmall,
                            color = if (currentDestination == it.route) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(6.dp)
                                .background(MaterialTheme.colorScheme.primary)
                        ) {
                            IconButton(
                                onClick = { navController.navigate("$CREATE_PROJECT_ROUTE/{id}") },
                                colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary)
                            ) {
                                Icon(
                                    painter = painterResource(it.activeIcon),
                                    contentDescription = stringResource(it.screenName),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}