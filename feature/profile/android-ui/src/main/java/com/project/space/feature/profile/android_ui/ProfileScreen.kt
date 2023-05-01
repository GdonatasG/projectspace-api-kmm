package com.project.space.feature.profile.android_ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Profile") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .imePadding()
                .captionBarPadding()
        ) {
            Text(text = "ProfileScreen")
        }
    }
}
