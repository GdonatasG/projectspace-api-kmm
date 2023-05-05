package com.project.space.components.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.project.space.components.button.FullWidthButton

@Composable
fun EmptyView(title: String, message: String, onRefresh: (() -> Unit)? = null) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = message, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
        }
        onRefresh?.let {
            FullWidthButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                title = "Refresh",
                onClick = onRefresh
            )
        }

    }
}
