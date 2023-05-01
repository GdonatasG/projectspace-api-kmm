package com.project.space.feature.createproject.android_ui

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProjectScreen(viewModel: CreateProjectViewModel) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    val state by viewModel.state.collectAsState()

    val name by viewModel.name.collectAsState()
    val nameError by viewModel.nameError.collectAsState()

    val description by viewModel.description.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Create project") },
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
                .imePadding()
                .captionBarPadding()
        ) {
            Box(modifier = Modifier.clickable(interactionSource = interactionSource, indication = null) {
                focusManager.clearFocus(true)
            }) {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(), value = name,
                            onValueChange = { value ->
                                viewModel.onNameChanged(value)
                            },
                            label = {
                                Text(text = "Name (required)")
                            },
                            singleLine = true, isError = nameError != null,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            })
                        )
                        androidx.compose.animation.AnimatedVisibility(visible = nameError != null) {
                            nameError?.let { error ->
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
                            modifier = Modifier.fillMaxWidth(), value = description,
                            onValueChange = { value ->
                                viewModel.onDescriptionChanged(value)
                            },
                            label = {
                                Text(text = "Description")
                            },
                            singleLine = false,
                            maxLines = 5,
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus(true)
                                }
                            )
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        FullWidthButton(
                            title = "Create",
                            isLoading = state is CreateProjectViewModel.ViewState.Loading
                        ) {
                            viewModel.onCreate()
                        }
                    }
                }

            }
        }
    }

}
