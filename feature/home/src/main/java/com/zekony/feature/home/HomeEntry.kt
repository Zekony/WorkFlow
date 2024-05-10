package com.zekony.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zekony.feature.home.ui.HomeScreen

const val HOME_ROUTE = "home"

fun NavGraphBuilder.homeEntry(

) {
    composable(HOME_ROUTE) {
        HomeScreen()
    }
}