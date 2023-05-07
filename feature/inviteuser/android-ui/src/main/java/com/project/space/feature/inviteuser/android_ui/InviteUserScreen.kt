package com.project.space.feature.inviteuser.android_ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.project.space.components.button.FullWidthButton
import com.project.space.components.button.NavigateBackButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InviteUserScreen(viewModel: InviteUserViewModel) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    val state by viewModel.state.collectAsState()

    val email by viewModel.email.collectAsState()
    val emailError by viewModel.emailError.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Invite user") },
            navigationIcon = {
                NavigateBackButton(onClick = {
                    viewModel.onNavigateBack()
                })
            },
        )
    }) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .navigationBarsPadding()
                .imePadding()
                .captionBarPadding()
        ) {
            Box(modifier = Modifier.clickable(interactionSource = interactionSource, indication = null) {
                focusManager.clearFocus(true)
            }) {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(), contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(), value = email,
                            onValueChange = { value ->
                                viewModel.onEmailChanged(value)
                            },
                            label = {
                                Text(text = "Email")
                            },
                            singleLine = true,
                            keyboardActions = KeyboardActions(onDone = {
                                focusManager.clearFocus(true)
                            }),
                        )
                        androidx.compose.animation.AnimatedVisibility(visible = emailError != null) {
                            emailError?.let { error ->
                                Text(
                                    text = error,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        FullWidthButton(
                            title = "Invite", isLoading = state is InviteUserViewModel.ViewState.Loading
                        ) {
                            viewModel.onInviteUser()
                        }
                    }
                }
            }
        }
    }
}
