package com.project.space.components.navigation

import androidx.navigation.NavOptions
import com.ramcosta.composedestinations.spec.Direction

data class NavigationAction(
    val destination: Direction,
    val navOptions: NavOptions = NavOptions.Builder().build(),
    val popUpAll: Boolean = true
)
