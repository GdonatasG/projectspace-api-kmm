package com.project.space.presentation.screen

import androidx.compose.runtime.Composable
import com.project.space.feature.splashscreen.android_ui.SplashViewModel
import com.project.space.presentation.navigation.MainNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.getViewModel

@Composable
@Destination
@MainNavGraph(start = true)
fun SplashScreen(viewModel: SplashViewModel = getViewModel()) {
    com.project.space.feature.splashscreen.android_ui.SplashScreen(viewModel = viewModel)
}
