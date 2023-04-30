package com.project.space.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen() {
    Scaffold(modifier = Modifier.statusBarsPadding()) {
        Text(text = "TasksScreen")
    }
}
