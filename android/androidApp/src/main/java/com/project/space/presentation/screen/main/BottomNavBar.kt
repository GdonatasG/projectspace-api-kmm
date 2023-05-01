package com.project.space.presentation.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination

@Composable
fun BottomNavBar(navController: NavController, tabs: List<Tab>) {
    NavigationBar(
        modifier = Modifier
            .shadow(6.dp),
        containerColor = Color.White
    ) {
        val current by navController.currentBottomNavScreenAsState()
        tabs.forEach { tab ->
            NavigationBarItem(
                icon = { Image(imageVector = tab.icon, contentDescription = tab.label) },
                selected = current.route == tab.route,
                label = { Text(text = tab.label) },
                onClick = {
                    navController.navigate(tab.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.findStartDestination().route!!) {
                            saveState = true
                        }
                    }
                }
            )

        }
    }
}

enum class Tab(val route: String, val label: String, val icon: ImageVector) {
    DASHBOARD(route = "dashboardTab", label = "Dashboard", icon = Icons.Outlined.Dashboard),
    TASKS(route = "tasksTab", label = "Tasks", icon = Icons.Outlined.Task),
    PROJECTS(route = "projectsTab", label = "Projects", icon = Icons.Outlined.Article),
    PROFILE(route = "profileTab", label = "Profile", icon = Icons.Outlined.AccountCircle)
}

@Stable
@Composable
private fun NavController.currentBottomNavScreenAsState(): State<Tab> {
    val selectedItem = remember { mutableStateOf(Tab.DASHBOARD) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == Tab.DASHBOARD.route } -> {
                    selectedItem.value = Tab.DASHBOARD
                }
                destination.hierarchy.any { it.route == Tab.TASKS.route } -> {
                    selectedItem.value = Tab.TASKS
                }
                destination.hierarchy.any { it.route == Tab.PROJECTS.route } -> {
                    selectedItem.value = Tab.PROJECTS
                }
                destination.hierarchy.any { it.route == Tab.PROFILE.route } -> {
                    selectedItem.value = Tab.PROFILE
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}
