package com.project.space.presentation.screen

import androidx.compose.runtime.Composable
import com.project.space.components.utils.getLifecycleViewModel
import com.project.space.feature.splashscreen.android_ui.SplashViewModel
import com.project.space.presentation.navigation.MainNavGraph
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
@MainNavGraph(start = true)
fun SplashScreen(viewModel: SplashViewModel = getLifecycleViewModel()) {
    com.project.space.feature.splashscreen.android_ui.SplashScreen(viewModel = viewModel)
}
