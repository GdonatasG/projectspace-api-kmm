package com.project.space.feature.createtask.android_ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.project.space.components.button.NavigateBackButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(viewModel: CreateTaskViewModel) {
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


        }

    }
}
