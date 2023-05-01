package com.project.space.feature.profile.android_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.space.components.ListTile

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val generalState by viewModel.generalState.collectAsState()
    val refreshingGeneral by viewModel.refreshingGeneral.collectAsState()

    val invitationsState by viewModel.invitationsState.collectAsState()
    val refreshingInvitations by viewModel.refreshingInvitations.collectAsState()

    val listState = rememberLazyListState()

    val pullRefreshState =
        rememberPullRefreshState(refreshing = refreshingGeneral || refreshingInvitations, onRefresh = {
            viewModel.onRefresh()
        })

    Scaffold(topBar = {
        Column(modifier = Modifier.fillMaxWidth()) {
            CenterAlignedTopAppBar(title = { Text(text = "Profile") })
            when (val generalType = generalState) {
                is ProfileViewModel.GeneralViewState.Content -> {
                    generalType.user?.let { user ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(horizontal = 16.dp)
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Hello, " + user.username + "!",
                                style = MaterialTheme.typography.titleMedium.copy(fontSize = 19.sp)
                            )
                        }
                    }
                }
                else -> {}
            }
        }
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .imePadding()
                .captionBarPadding()
                .pullRefresh(pullRefreshState)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxHeight(), state = listState
            ) {
                item {
                    ListTile(title = "Edit profile", leading = {
                        Image(imageVector = Icons.Outlined.AccountCircle, contentDescription = "Edit profile")
                    }, trailing = {
                        Image(imageVector = Icons.Default.ArrowRight, contentDescription = "Edit profile")
                    }, onClick = {
                        viewModel.onNavigateToEditProfile()
                    }, topDivider = true
                    )
                }
                item {
                    ListTile(title = when (val invitationsType = invitationsState) {
                        is ProfileViewModel.InvitationsViewState.Content -> "Invitations (${invitationsType.count})"
                        else -> "Invitations"
                    }, leading = {
                        Image(imageVector = Icons.Outlined.Mail, contentDescription = "See invitations")
                    }, trailing = {
                        Image(imageVector = Icons.Default.ArrowRight, contentDescription = "See invitations")
                    }, onClick = {
                        viewModel.onNavigateToInvitations()
                    })
                }
                item {
                    ListTile(title = "Logout", leading = {
                        Image(imageVector = Icons.Outlined.ExitToApp, contentDescription = "Logout")
                    }, onClick = {
                        viewModel.onLogout()
                    })
                }
            }

            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = refreshingGeneral || refreshingInvitations,
                state = pullRefreshState,
                contentColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}
