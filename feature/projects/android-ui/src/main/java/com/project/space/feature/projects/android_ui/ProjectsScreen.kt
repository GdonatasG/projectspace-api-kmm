package com.project.space.feature.projects.android_ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.project.space.components.ListTile
import com.project.space.components.view.EmptyView
import com.project.space.components.view.ErrorView
import com.project.space.components.view.LoadingView
import com.project.space.feature.projects.domain.Project

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(viewModel: ProjectsViewModel) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs: List<String> = listOf("My projects", "Others")

    val state by viewModel.state.collectAsState()

    Scaffold {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
                .captionBarPadding()
        ) {
            CenterAlignedTopAppBar(title = { Text(text = "Projects") }, actions = {
                IconButton(onClick = {

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
                        selectedTabIndex = index
                        viewModel.onTabChange(
                            if (selectedTabIndex == 0)
                                com.project.space.feature.projects.Tab.MY_PROJECTS
                            else
                                com.project.space.feature.projects.Tab.OTHERS
                        )
                    })
                }
            }
            when (val type = state) {
                is ProjectsViewModel.ViewState.Content -> Content(data = type.data,
                    delegate = object : ContentDelegate {
                        override fun onProjectClick(project: Project) {

                        }
                    })
                is ProjectsViewModel.ViewState.Loading -> LoadingView()
                is ProjectsViewModel.ViewState.Empty -> EmptyView(
                    title = type.title,
                    message = type.message,
                    onRefresh = viewModel::onRetry
                )
                is ProjectsViewModel.ViewState.Error -> ErrorView(
                    title = type.title,
                    message = type.message,
                    onRetry = viewModel::onRetry
                )
            }
        }
    }
}

@Composable
private fun Content(data: List<Project>, delegate: ContentDelegate) {
    LazyColumn {
        itemsIndexed(data, key = { _, project ->
            project.id
        }) { index, project ->
            ListTile(
                title = project.name,
                description = project.description.ifEmpty { null },
                divided = index < data.size - 1,
                trailing = { Image(imageVector = Icons.Default.ArrowRight, contentDescription = "Change project") },
                onClick = {
                    delegate.onProjectClick(project)
                }
            )
        }
    }
}

private interface ContentDelegate {
    fun onProjectClick(project: Project)
}
