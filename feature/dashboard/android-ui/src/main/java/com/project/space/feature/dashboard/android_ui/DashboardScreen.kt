package com.project.space.feature.dashboard.android_ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.space.components.view.EmptyView
import com.project.space.components.view.ErrorView
import com.project.space.components.view.LoadingView
import com.project.space.feature.dashboard.ProjectRisk
import me.bytebeats.views.charts.pie.PieChart
import me.bytebeats.views.charts.pie.PieChartData

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val statisticsState by viewModel.statisticsViewState.collectAsState()
    val riskState by viewModel.riskViewState.collectAsState()
    val membersState by viewModel.membersViewState.collectAsState()
    val selectedProjectState by viewModel.selectedProjectState.collectAsState()

    val statisticsRefreshing by viewModel.statisticsRefreshing.collectAsState()
    val riskRefreshing by viewModel.riskRefreshing.collectAsState()
    val membersRefreshing by viewModel.membersRefreshing.collectAsState()

    val listState = rememberLazyListState()

    val pullRefreshState =
        rememberPullRefreshState(refreshing = statisticsRefreshing || riskRefreshing || membersRefreshing, onRefresh = {
            viewModel.onRefresh()
        })



    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            if (selectedProjectState is DashboardViewModel.SelectedProjectViewState.Selected) {
                Text(text = (selectedProjectState as DashboardViewModel.SelectedProjectViewState.Selected).project.name)
            }
        })
    }) {
        Box(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .imePadding()
                .captionBarPadding()
        ) {
            when (val projectType = selectedProjectState) {
                is DashboardViewModel.SelectedProjectViewState.Selected -> {
                    Box(
                        modifier = Modifier.pullRefresh(pullRefreshState)
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxHeight(),
                            state = listState,
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            val totalTasks: String =
                                if (statisticsState is DashboardViewModel.StatisticsViewState.Content) (statisticsState as DashboardViewModel.StatisticsViewState.Content).data.totalTasks.toString()
                                else ""

                            item {
                                SectionTitle(title = "TASKS" + if (totalTasks.isNotEmpty()) " ($totalTasks)" else "")
                                Spacer(modifier = Modifier.height(12.dp))
                                Statistics(state = statisticsState)
                            }
                            item {
                                Spacer(modifier = Modifier.height(20.dp))
                                SectionTitle(title = "MONTHLY RISK")
                                Spacer(modifier = Modifier.height(12.dp))
                                Risk(state = riskState)
                            }
                            item {
                                Spacer(modifier = Modifier.height(20.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Row(modifier = Modifier.weight(1f)) {
                                        SectionTitle(title = "TEAM")
                                        if (membersState is DashboardViewModel.MembersViewState.Loading) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(16.dp),
                                                strokeWidth = 2.dp
                                            )
                                        }
                                    }
                                    IconButton(onClick = { viewModel.onNavigateToNewInvitation() }) {
                                        Icon(imageVector = Icons.Outlined.Add, contentDescription = "Invite new member")
                                    }
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Team(state = membersState)
                            }
                        }

                        PullRefreshIndicator(
                            modifier = Modifier.align(Alignment.TopCenter),
                            refreshing = statisticsRefreshing || riskRefreshing || membersRefreshing,
                            state = pullRefreshState,
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                is DashboardViewModel.SelectedProjectViewState.None -> EmptyView(
                    title = projectType.title, message = projectType.message
                )
            }
        }

    }
}

@Composable
private fun Statistics(state: DashboardViewModel.StatisticsViewState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp), colors = CardDefaults.cardColors(
            containerColor = Color.White
        ), elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is DashboardViewModel.StatisticsViewState.Loading -> Column(
                    horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                }
                is DashboardViewModel.StatisticsViewState.Content -> {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier.weight(0.5f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            PieChart(
                                modifier = Modifier
                                    .size(100.dp), pieChartData = PieChartData(
                                    slices = listOf(
                                        PieChartData.Slice(
                                            value = state.data.openTasks.toFloat(), color = Color(0xFFF92E2E)
                                        ), PieChartData.Slice(
                                            value = state.data.closedTasks.toFloat(), color = Color(0xFF37D751)
                                        )
                                    )
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(
                            modifier = Modifier.weight(0.5f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Open tasks (${state.data.openTasks})",
                                style = MaterialTheme.typography.titleSmall.copy(color = Color(0xFFF92E2E)),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Closed tasks (${state.data.closedTasks})",
                                style = MaterialTheme.typography.titleSmall.copy(color = Color(0xFF37D751)),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                is DashboardViewModel.StatisticsViewState.Error -> {
                    Text(text = state.title, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = state.message, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun Risk(state: DashboardViewModel.RiskViewState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp), colors = CardDefaults.cardColors(
            containerColor = Color.White
        ), elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is DashboardViewModel.RiskViewState.Loading -> Column(
                    horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                }
                is DashboardViewModel.RiskViewState.Content -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier.weight(0.5f),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Risk level")
                            Icon(
                                modifier = Modifier.size(64.dp),
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Risk level",
                                tint = state.data.risk.getColor()
                            )
                            Text(text = state.data.risk.getVisualName(), style = MaterialTheme.typography.titleSmall)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(
                            modifier = Modifier.weight(0.5f),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "${state.data.openTasks}/${state.data.totalTasks}",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = state.data.risk.getColor(),
                                    fontWeight = FontWeight.W600,
                                    fontSize = 20.sp
                                ),
                                textAlign = TextAlign.Center
                            )
                            Text(text = "incomplete tasks this month", textAlign = TextAlign.Center)
                        }
                    }
                }
                is DashboardViewModel.RiskViewState.Error -> {
                    Text(text = state.title, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = state.message, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center
                    )
                }
            }

        }
    }
}

@Composable
private fun Team(state: DashboardViewModel.MembersViewState) {
    when (state) {
        is DashboardViewModel.MembersViewState.Content -> {
            LazyRow(
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsIndexed(state.data, key = { _, member -> member.id }) { index, member ->
                    Column(
                        modifier = Modifier.width(80.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = member.username)
                        Text(
                            text = member.username,
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = member.memberLevel.getVisualName(),
                            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.primary),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    if (index < state.data.size - 1) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
        else -> {}
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title, style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary)
    )
}

@Composable
private fun ProjectRisk.getColor(): Color = when (this) {
    ProjectRisk.UNKNOWN, ProjectRisk.NO_RISK -> Color.Unspecified
    ProjectRisk.LOW -> Color(0xFFBA63D9)
    ProjectRisk.MEDIUM -> Color(0xFFF9A543)
    ProjectRisk.HIGH -> Color(0xFFF92E2E)
}
