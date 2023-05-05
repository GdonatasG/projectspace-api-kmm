package com.project.space.feature.tasks.android_ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.project.space.components.ListTile
import com.project.space.components.view.EmptyView
import com.project.space.components.view.ErrorView
import com.project.space.components.view.LoadingView
import com.project.space.feature.tasks.domain.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(viewModel: TasksViewModel) {
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()
    val tabs: List<String> = listOf("My tasks", "All tasks")

    val state by viewModel.state.collectAsState()
    val selectedProjectState by viewModel.selectedProjectState.collectAsState()
    val refreshing by viewModel.refreshing.collectAsState()

    Scaffold(topBar = {
        Column(modifier = Modifier.fillMaxWidth()) {
            CenterAlignedTopAppBar(title = {
                if (selectedProjectState is TasksViewModel.SelectedProjectViewState.Selected) {
                    Text(text = (selectedProjectState as TasksViewModel.SelectedProjectViewState.Selected).project.name)
                }
            }, actions = {
                IconButton(
                    enabled = selectedProjectState is TasksViewModel.SelectedProjectViewState.Selected,
                    onClick = {
                        viewModel.onNavigateToCreateTask()
                    }
                ) {
                    Image(imageVector = Icons.Default.Add, contentDescription = "Create task")
                }
            })
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(),
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        enabled = selectedProjectState is TasksViewModel.SelectedProjectViewState.Selected,
                        text = { Text(title) },
                        selected = index == selectedTabIndex,
                        onClick = {
                            viewModel.onTabChange(index)
                        })
                }
            }
        }
    }) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .imePadding()
                .captionBarPadding()
        ) {
            when (val projectType = selectedProjectState) {
                is TasksViewModel.SelectedProjectViewState.Selected -> {
                    when (val type = state) {
                        is TasksViewModel.ViewState.Content -> Content(
                            data = type.data,
                            refreshing = refreshing,
                            delegate = object : ContentDelegate {
                                override fun onRefresh() {
                                    viewModel.onRefresh()
                                }
                            })
                        is TasksViewModel.ViewState.Loading -> LoadingView()
                        is TasksViewModel.ViewState.Empty -> EmptyView(
                            title = type.title, message = type.message, onRefresh = viewModel::onRetry
                        )
                        is TasksViewModel.ViewState.Error -> ErrorView(
                            title = type.title, message = type.message, onRetry = viewModel::onRetry
                        )
                    }

                }
                is TasksViewModel.SelectedProjectViewState.None -> EmptyView(
                    title = projectType.title, message = projectType.message
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Content(data: List<Task>, refreshing: Boolean, delegate: ContentDelegate) {
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
            state = listState,
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(data, key = { _, task -> task.id }) { index, task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.elevatedCardElevation()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = task.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleMedium
                        )
                        task.description.takeIf { it.trim().isNotEmpty() }?.let { description ->
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = description,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        if (task.startDate != null || task.endDate != null) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            task.startDate?.let { startDate ->
                                DateCard(date = "From: $startDate")
                            }
                            task.endDate?.let { endDate ->
                                DateCard(date = "Due to: $endDate")
                            }
                        }
                    }
                }
                if (index < data.size - 1) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
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

@Composable
private fun DateCard(date: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "Date"
            )
            Text(text = date, style = MaterialTheme.typography.labelMedium)
        }
    }
}

private interface ContentDelegate {
    fun onRefresh()
}
