package com.project.space.feature.projects.android_ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.project.space.components.ListTile
import com.project.space.components.view.EmptyView
import com.project.space.components.view.ErrorView
import com.project.space.components.view.LoadingView
import com.project.space.feature.projects.domain.Project

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(viewModel: ProjectsViewModel) {
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()
    val tabs: List<String> = listOf("My projects", "Others")

    val state by viewModel.state.collectAsState()
    val refreshing by viewModel.refreshing.collectAsState()

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                CenterAlignedTopAppBar(title = { Text(text = "Projects") }, actions = {
                    IconButton(onClick = {
                        viewModel.onNavigateToCreateProject()
                    }) {
                        Image(imageVector = Icons.Default.Add, contentDescription = "Create Project")
                    }
                })
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(text = { Text(title) }, selected = index == selectedTabIndex, onClick = {
                            viewModel.onTabChange(index)
                        })
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .imePadding()
                .captionBarPadding()
        ) {
            when (val type = state) {
                is ProjectsViewModel.ViewState.Content -> Content(
                    data = type.data,
                    refreshing = refreshing,
                    delegate = object : ContentDelegate {
                        override fun onRefresh() {
                            viewModel.onRefresh()
                        }

                        override fun onProjectClick(project: Project) {
                            viewModel.setSelectedProject(project)
                        }
                    })
                is ProjectsViewModel.ViewState.Loading -> LoadingView()
                is ProjectsViewModel.ViewState.Empty -> EmptyView(
                    title = type.title, message = type.message, onRefresh = viewModel::onRetry
                )
                is ProjectsViewModel.ViewState.Error -> ErrorView(
                    title = type.title, message = type.message, onRetry = viewModel::onRetry
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Content(data: List<Project>, refreshing: Boolean, delegate: ContentDelegate) {
    val listState = rememberLazyListState()

    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
        delegate.onRefresh()
    })

    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(),
            state = listState
        ) {
            itemsIndexed(data, key = { _, project ->
                project.id
            }) { index, project ->
                ListTile(title = project.name,
                    description = project.description.trim().ifEmpty { null },
                    trailing = {
                        if (!project.selected) {
                            Image(imageVector = Icons.Default.ArrowRight, contentDescription = "Change project")
                        } else {
                            Image(imageVector = Icons.Default.Check, contentDescription = "Selected project")
                        }
                    },
                    onClick = {
                        delegate.onProjectClick(project)
                    })
            }
        }

        PullRefreshIndicator(
            modifier = Modifier
                .align(Alignment.TopCenter),
            refreshing = refreshing,
            state = pullRefreshState,
            contentColor = MaterialTheme.colorScheme.primary
        )
    }
}

private interface ContentDelegate {
    fun onRefresh()
    fun onProjectClick(project: Project)
}
