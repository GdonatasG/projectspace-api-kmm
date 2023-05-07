package com.project.space.feature.userinvitations.android_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
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
import com.project.space.components.ListTile
import com.project.space.components.button.NavigateBackButton
import com.project.space.components.view.EmptyView
import com.project.space.components.view.ErrorView
import com.project.space.components.view.LoadingOverlay
import com.project.space.components.view.LoadingView

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun UserInvitationsScreen(viewModel: UserInvitationsViewModel) {
    val state by viewModel.state.collectAsState()
    val acceptState by viewModel.acceptState.collectAsState()
    val refreshing by viewModel.refreshing.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "My Invitations") },
                navigationIcon = {
                    NavigateBackButton(
                        onClick = {
                            viewModel.onNavigateBack()
                        }
                    )
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .navigationBarsPadding()
                .imePadding()
                .captionBarPadding()
        ) {
            when (val type = state) {
                is UserInvitationsViewModel.ViewState.Content -> {
                    val listState = rememberLazyListState()

                    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
                        viewModel.onRefresh()
                    })

                    Box {
                        Box(
                            modifier = Modifier
                                .pullRefresh(pullRefreshState)
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxHeight(),
                                state = listState
                            ) {
                                itemsIndexed(type.data) { index, invitation ->
                                    ListTile(
                                        title = invitation.projectName,
                                        description = "Owner: ${invitation.ownerName}",
                                        trailing = {
                                            Image(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Accept invitation"
                                            )
                                        },
                                        onClick = {
                                            viewModel.onAccept(invitation)
                                        },
                                        topDivider = index == 0
                                    )
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

                        if (acceptState is UserInvitationsViewModel.AcceptViewState.Loading) {
                            LoadingOverlay()
                        }
                    }
                }
                is UserInvitationsViewModel.ViewState.Loading -> LoadingView()
                is UserInvitationsViewModel.ViewState.Empty -> EmptyView(
                    title = type.title, message = type.message, onRefresh = viewModel::onRetry
                )
                is UserInvitationsViewModel.ViewState.Error -> ErrorView(
                    title = type.title, message = type.message, onRetry = viewModel::onRetry
                )
            }

        }

    }
}
