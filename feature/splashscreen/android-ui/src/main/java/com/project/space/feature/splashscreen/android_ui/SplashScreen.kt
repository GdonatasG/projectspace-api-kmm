package com.project.space.feature.splashscreen.android_ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun SplashScreen(viewModel: SplashViewModel) {
    LaunchedEffect(true) {
        viewModel.startAppFlow()
    }
}
