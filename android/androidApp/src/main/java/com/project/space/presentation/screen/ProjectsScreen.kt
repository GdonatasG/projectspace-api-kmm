package com.project.space.presentation.screen

import androidx.compose.runtime.Composable
import com.project.space.components.utils.getLifecycleViewModel
import com.project.space.feature.projects.android_ui.ProjectsViewModel

@Composable
fun ProjectsScreen(viewModel: ProjectsViewModel = getLifecycleViewModel()) {
    com.project.space.feature.projects.android_ui.ProjectsScreen(viewModel = viewModel)
}
