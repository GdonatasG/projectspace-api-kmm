package com.project.space.feature.createtask.android_ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.project.space.components.button.FullWidthButton
import com.project.space.components.button.NavigateBackButton
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(viewModel: CreateTaskViewModel) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    val state by viewModel.state.collectAsState()
    val selectedProject by viewModel.selectedProjectState.collectAsState()

    val title by viewModel.title.collectAsState()
    val titleError by viewModel.titleError.collectAsState()

    val description by viewModel.description.collectAsState()

    val priority by viewModel.priority.collectAsState()
    val priorityError by viewModel.priorityError.collectAsState()

    val selectedStartDate by viewModel.selectedStartDate.collectAsState()
    var formattedStartDate by remember { mutableStateOf("") }

    val selectedEndDate by viewModel.selectedEndDate.collectAsState()
    var formattedEndDate by remember { mutableStateOf("") }

    LaunchedEffect(selectedStartDate, selectedEndDate) {
        formattedStartDate = formatDate(selectedStartDate)
        formattedEndDate = formatDate(selectedEndDate)
    }

    val startDateDialogState = rememberMaterialDialogState()
    val endDateDialogState = rememberMaterialDialogState()


    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Create task") },
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
                .imePadding()
                .captionBarPadding()
        ) {
            Box(modifier = Modifier.clickable(interactionSource = interactionSource, indication = null) {
                focusManager.clearFocus(true)
            }) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)
                ) {
                    when (val project = selectedProject) {
                        is CreateTaskViewModel.SelectedProjectViewState.Selected -> {
                            item {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Add new task to project \"${project.project.name}\"",
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                        else -> {}
                    }
                    item {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = title,
                            onValueChange = { value ->
                                viewModel.onTitleChanged(value)
                            },
                            label = {
                                Text(text = "Title (required)")
                            },
                            singleLine = true,
                            isError = titleError != null,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            })
                        )
                        androidx.compose.animation.AnimatedVisibility(visible = titleError != null) {
                            titleError?.let { error ->
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
                            modifier = Modifier.fillMaxWidth(),
                            value = description,
                            onValueChange = { value ->
                                viewModel.onDescriptionChanged(value)
                            },
                            label = {
                                Text(text = "Description")
                            },
                            singleLine = false,
                            maxLines = 5,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            })
                        )
                    }
                    item {
                        Box(modifier = Modifier.clickable(indication = null,
                            interactionSource = remember { MutableInteractionSource() }) {
                            if (state !is CreateTaskViewModel.ViewState.Loading) {
                                viewModel.onNavigateToPrioritySelection()
                            }
                        }) {
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = priority,
                                onValueChange = { },
                                label = {
                                    Text(text = "Priority (required)")
                                },
                                isError = priorityError != null,
                                singleLine = true,
                                maxLines = 1,
                                enabled = false,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledLeadingIconColor = MaterialTheme.colorScheme.outline,
                                    disabledTrailingIconColor = MaterialTheme.colorScheme.outline,
                                    disabledSupportingTextColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSurface,
                                )
                            )
                        }
                        androidx.compose.animation.AnimatedVisibility(visible = priorityError != null) {
                            priorityError?.let { error ->
                                Text(
                                    text = error,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                    item {
                        Box(modifier = Modifier.clickable(indication = null,
                            interactionSource = remember { MutableInteractionSource() }) {
                            if (state !is CreateTaskViewModel.ViewState.Loading) {
                                startDateDialogState.show()
                            }
                        }) {
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = formattedStartDate,
                                onValueChange = { },
                                label = {
                                    Text(text = "Start Date")
                                },
                                singleLine = true,
                                maxLines = 1,
                                enabled = false,
                                leadingIcon = {
                                    Icon(Icons.Default.CalendarMonth, contentDescription = "Start date")
                                },
                                trailingIcon = {
                                    if (selectedStartDate != null) {
                                        IconButton(onClick = {
                                            viewModel.onStartDateChanged(null)
                                        }) {
                                            Icon(Icons.Default.Clear, contentDescription = "Clear Start Date")
                                        }
                                    }
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledSupportingTextColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSurface,
                                )
                            )
                        }
                    }
                    item {
                        Box(modifier = Modifier.clickable(indication = null,
                            interactionSource = remember { MutableInteractionSource() }) {
                            if (state !is CreateTaskViewModel.ViewState.Loading) {
                                endDateDialogState.show()
                            }
                        }) {
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = formattedEndDate,
                                onValueChange = { },
                                label = {
                                    Text(text = "End Date")
                                },
                                singleLine = true,
                                maxLines = 1,
                                enabled = false,
                                leadingIcon = {
                                    Icon(Icons.Default.CalendarMonth, contentDescription = "End date")
                                },
                                trailingIcon = {
                                    if (selectedEndDate != null) {
                                        IconButton(onClick = {
                                            viewModel.onEndDateChanged(null)
                                        }) {
                                            Icon(Icons.Default.Clear, contentDescription = "Clear End Date")
                                        }
                                    }
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledSupportingTextColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSurface,
                                )
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        FullWidthButton(
                            title = "Create", isLoading = state is CreateTaskViewModel.ViewState.Loading
                        ) {
                            viewModel.onCreateTask()
                        }
                    }
                }
            }
        }
    }
    MaterialDialog(
        dialogState = startDateDialogState,
        buttons = {
            positiveButton(text = "Select")
            negativeButton(text = "Cancel")
        }
    ) {
        this.datepicker(
            initialDate = selectedStartDate ?: LocalDate.now(),
            allowedDateValidator = {
                it >= LocalDate.now()
            },
            onDateChange = {
                viewModel.onStartDateChanged(it)
            }
        )
    }
    MaterialDialog(
        dialogState = endDateDialogState,
        buttons = {
            positiveButton(text = "Select")
            negativeButton(text = "Cancel")
        }
    ) {
        val initialDate =
            if ((selectedEndDate ?: LocalDate.now()) < (selectedStartDate ?: LocalDate.now())) selectedStartDate
                ?: LocalDate.now() else selectedEndDate
                ?: LocalDate.now()
        this.datepicker(
            initialDate = initialDate,
            allowedDateValidator = {
                if (selectedStartDate != null) {
                    return@datepicker it >= selectedStartDate
                }
                return@datepicker it >= LocalDate.now()
            },
            onDateChange = {
                viewModel.onEndDateChanged(it)
            }
        )
    }
}

private fun formatDate(date: LocalDate?): String {
    return if (date == null) "" else DateTimeFormatter
        .ofPattern("MMM dd")
        .format(date)
}
