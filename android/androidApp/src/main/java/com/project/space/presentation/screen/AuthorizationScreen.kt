package com.project.space.presentation.screen

import androidx.compose.runtime.Composable
import com.project.space.presentation.navigation.MainNavGraph
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
@MainNavGraph
fun AuthorizationScreen() {
    com.project.space.feature.authorization.android_ui.AuthorizationScreen()
}
