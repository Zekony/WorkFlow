package com.finto.feature.preview

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.finto.feature.preview.ui.PreviewScreen

const val PREVIEW_ROUTE = "preview"

fun NavGraphBuilder.previewEntry(
    navigate: () -> Unit
) {
    composable(PREVIEW_ROUTE) {
        PreviewScreen(navigate)
    }
}