package com.project.space.feature.editprofile.android_ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.project.space.components.button.FullWidthButton
import com.project.space.components.button.NavigateBackButton
import com.project.space.components.view.ErrorView
import com.project.space.components.view.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(viewModel: EditProfileViewModel) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    val state by viewModel.state.collectAsState()
    val updateState by viewModel.updateState.collectAsState()

    val username by viewModel.username.collectAsState()

    val firstName by viewModel.firstName.collectAsState()
    val firstNameError by viewModel.firstNameError.collectAsState()

    val lastName by viewModel.lastName.collectAsState()
    val lastNameError by viewModel.lastNameError.collectAsState()

    val email by viewModel.email.collectAsState()

    val organizationName by viewModel.organizationName.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Edit profile") },
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
                when (val type = state) {
                    is EditProfileViewModel.ViewState.Loading -> LoadingView()
                    is EditProfileViewModel.ViewState.Error -> ErrorView(
                        title = type.title, message = type.message, onRetry = viewModel::onRetry
                    )
                    else -> LazyColumn(
                        modifier = Modifier.fillMaxHeight(), contentPadding = PaddingValues(16.dp)
                    ) {
                        item {
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(), value = username,
                                onValueChange = { },
                                label = {
                                    Text(text = "Username")
                                },
                                singleLine = true, enabled = false,
                            )
                        }
                        item {
                            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                                value = firstName,
                                onValueChange = { value ->
                                    viewModel.onFirstNameChanged(value)
                                },
                                label = {
                                    Text(text = "First name")
                                },
                                singleLine = true,
                                isError = firstNameError != null,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(onNext = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                })
                            )
                            androidx.compose.animation.AnimatedVisibility(visible = firstNameError != null) {
                                firstNameError?.let { error ->
                                    Text(
                                        text = error,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                        item {
                            OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                                value = lastName,
                                onValueChange = { value ->
                                    viewModel.onLastNameChanged(value)
                                },
                                label = {
                                    Text(text = "Last name")
                                },
                                singleLine = true,
                                isError = lastNameError != null,
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(onNext = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                })
                            )
                            androidx.compose.animation.AnimatedVisibility(visible = lastNameError != null) {
                                lastNameError?.let { error ->
                                    Text(
                                        text = error,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                        item {
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(), value = email,
                                onValueChange = { },
                                label = {
                                    Text(text = "Email")
                                },
                                singleLine = true, enabled = false,
                            )
                        }
                        item {
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(), value = organizationName,
                                onValueChange = { value ->
                                    viewModel.onOrganizationNameChanged(value)
                                },
                                label = {
                                    Text(text = "Organization name")
                                },
                                singleLine = true,
                                keyboardActions = KeyboardActions(onDone = {
                                    focusManager.clearFocus(true)
                                }),
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(24.dp))
                            FullWidthButton(
                                title = "Save", isLoading = updateState is EditProfileViewModel.UpdateViewState.Loading
                            ) {
                                viewModel.onUpdateProfile()
                            }
                        }
                    }
                }

            }
        }

    }
}
