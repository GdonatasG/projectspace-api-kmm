package com.project.space.components.button

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.project.space.components.theme.primaryColor

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FullWidthButton(title: String, isLoading: Boolean = false, isEnabled: Boolean = true, onClick: () -> Unit) {
    val focusManager = LocalFocusManager.current

    val enabled = isEnabled && !isLoading

    val disabledAlpha: Float = if (isSystemInDarkTheme()) 0.4f else 0.6f

    ElevatedButton(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp),
        enabled = enabled,
        onClick = {
            focusManager.clearFocus(true)
            onClick.invoke()
        },
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = primaryColor,
            disabledContainerColor = primaryColor.copy(alpha = disabledAlpha),
            contentColor = Color.White,
            disabledContentColor = Color.White.copy(alpha = disabledAlpha)
        )
    ) {
        AnimatedContent(targetState = isLoading) { loading ->
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .size(22.dp),
                    color = LocalContentColor.current,
                    strokeWidth = 2.dp
                )

                return@AnimatedContent
            }

            Text(text = title)

        }
    }
}
