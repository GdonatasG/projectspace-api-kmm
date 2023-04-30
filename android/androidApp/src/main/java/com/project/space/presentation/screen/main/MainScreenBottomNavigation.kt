package com.project.space.presentation.screen.main

import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.space.presentation.screen.DashboardScreen
import com.project.space.presentation.screen.ProfileScreen
import com.project.space.presentation.screen.ProjectsScreen
import com.project.space.presentation.screen.TasksScreen

@Composable
fun MainScreenBottomNavigation(bottomNavHostController: NavHostController) {
    NavHost(
        modifier = Modifier.padding(bottom = 80.dp),
        navController = bottomNavHostController,
        startDestination = Tab.DASHBOARD.route
    ) {
        composable(Tab.DASHBOARD.route) {
            EnterAnimation {
                DashboardScreen()
            }
        }
        composable(Tab.TASKS.route) {
            EnterAnimation {
                TasksScreen()
            }
        }
        composable(Tab.PROJECTS.route) {
            EnterAnimation {
                ProjectsScreen()
            }
        }
        composable(Tab.PROFILE.route) {
            EnterAnimation {
                ProfileScreen()
            }
        }
    }
}

@Composable
private fun EnterAnimation(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(initialOffsetY = { -40 })
                + expandVertically(expandFrom = Alignment.Top)
                + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        content()
    }
}
