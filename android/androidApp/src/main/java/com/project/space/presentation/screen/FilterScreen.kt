package com.project.space.presentation.screen

import androidx.compose.runtime.Composable
import com.project.space.components.navigation.SlideFromSideTransition
import com.project.space.components.utils.getLifecycleViewModel
import com.project.space.feature.common.android_ui.FilterViewModel
import com.project.space.presentation.navigation.MainNavGraph
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination(style = SlideFromSideTransition::class)
@MainNavGraph
fun FilterScreen(viewModel: FilterViewModel = getLifecycleViewModel()) {
    com.project.space.feature.common.android_ui.FilterScreen(viewModel = viewModel)
}
