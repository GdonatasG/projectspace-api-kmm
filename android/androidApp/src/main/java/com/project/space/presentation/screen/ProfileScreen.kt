package com.project.space.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.project.space.components.utils.getLifecycleViewModel
import com.project.space.feature.profile.android_ui.ProfileViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = getLifecycleViewModel()) {
    com.project.space.feature.profile.android_ui.ProfileScreen(viewModel = viewModel)
}
