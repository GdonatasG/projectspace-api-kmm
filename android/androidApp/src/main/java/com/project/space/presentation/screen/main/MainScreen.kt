package com.project.space.presentation.screen.main

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.project.space.presentation.navigation.MainNavGraph
import com.ramcosta.composedestinations.annotation.Destination

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
@Destination
@MainNavGraph
fun MainScreen() {
    val bottomNavController = rememberAnimatedNavController()
    val tabs: List<Tab> = listOf(
        Tab.DASHBOARD, Tab.TASKS, Tab.PROJECTS, Tab.PROFILE
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = { BottomNavBar(navController = bottomNavController, tabs = tabs) }) {
        MainScreenBottomNavigation(paddingValues = it, bottomNavHostController = bottomNavController)
    }
}
