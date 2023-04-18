package com.project.space.presentation.navigation.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.libraries.alerts.Alert
import com.libraries.alerts.AlertController
import com.libraries.alerts.AlertState
import com.project.space.presentation.navigation.MainNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyle
import org.koin.androidx.compose.get

@Composable
@Destination(style = DestinationStyle.Dialog::class)
@MainNavGraph
fun Alert(alertState: AlertState = get(), navigator: AlertController = get()) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .background(color = Color.Transparent),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = alertState.getTitle(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (alertState.getMessage().isNotBlank()) Text(
                text = alertState.getMessage(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                alertState.getButtons().forEach { button ->
                    when (button.event) {
                        Alert.Button.Event.CANCEL -> {
                            NeutralButton(title = button.title) {
                                navigator.close()
                                button.onClick?.invoke()
                            }
                        }
                        Alert.Button.Event.DEFAULT, Alert.Button.Event.DESTRUCTIVE -> {
                            PositiveButton(title = button.title) {
                                navigator.close()
                                button.onClick?.invoke()
                            }
                        }
                    }
                }

            }
        }
    }
}


@Composable
private fun NeutralButton(title: String, onClick: () -> Unit) {
    Button(
        onClick = onClick, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface, contentColor = MaterialTheme.colorScheme.onSurface
        ), shape = MaterialTheme.shapes.large
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun PositiveButton(title: String, onClick: () -> Unit) {
    Button(
        onClick = onClick, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White
        ), shape = MaterialTheme.shapes.large
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
