package com.project.space.components.button

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun NavigateBackButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Image(imageVector = Icons.Default.ArrowBack, contentDescription = "Navigate back")
    }
}
