package com.project.space.presentation.screen

import androidx.compose.runtime.Composable
import com.project.space.components.navigation.SlideFromSideTransition
import com.project.space.components.utils.getLifecycleViewModel
import com.project.space.feature.createproject.android_ui.CreateProjectViewModel
import com.project.space.presentation.navigation.MainNavGraph
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination(style = SlideFromSideTransition::class)
@MainNavGraph
fun CreateProjectScreen(viewModel: CreateProjectViewModel = getLifecycleViewModel()) {
    com.project.space.feature.createproject.android_ui.CreateProjectScreen(viewModel = viewModel)
}
