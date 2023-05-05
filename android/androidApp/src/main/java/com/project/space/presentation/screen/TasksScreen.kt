package com.project.space.presentation.screen

import androidx.compose.runtime.Composable
import com.project.space.components.utils.getLifecycleViewModel
import com.project.space.feature.tasks.android_ui.TasksViewModel

@Composable
fun TasksScreen(viewModel: TasksViewModel = getLifecycleViewModel()) {
    com.project.space.feature.tasks.android_ui.TasksScreen(viewModel = viewModel)
}
