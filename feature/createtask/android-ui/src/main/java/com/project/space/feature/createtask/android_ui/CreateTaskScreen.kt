package com.project.space.feature.createtask.android_ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.project.space.components.button.FullWidthButton
import com.project.space.components.button.NavigateBackButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(viewModel: CreateTaskViewModel) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Create task") },
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
                        Spacer(modifier = Modifier.height(24.dp))
                        FullWidthButton(
                            title = "Add",
                        ) {
                            viewModel.onNavigateToPrioritySelection()
                        }
                    }
                }
            }

        }

    }
}
