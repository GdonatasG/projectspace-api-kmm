package com.project.space.presentation.screen

import androidx.compose.runtime.Composable
import com.project.space.R
import com.project.space.components.utils.getLifecycleViewModel
import com.project.space.feature.authorization.android_ui.AuthorizationViewModel
import com.project.space.presentation.navigation.MainNavGraph
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
@MainNavGraph
fun AuthorizationScreen(viewModel: AuthorizationViewModel = getLifecycleViewModel()) {
    com.project.space.feature.authorization.android_ui.AuthorizationScreen(
        logoResId = R.drawable.logo,
        viewModel = viewModel
    )
}
