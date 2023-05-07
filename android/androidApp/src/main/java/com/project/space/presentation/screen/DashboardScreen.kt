package com.project.space.presentation.screen

import androidx.compose.runtime.Composable
import com.project.space.components.utils.getLifecycleViewModel
import com.project.space.feature.dashboard.android_ui.DashboardViewModel

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = getLifecycleViewModel()) {
    com.project.space.feature.dashboard.android_ui.DashboardScreen(viewModel = viewModel)
}
