package com.project.space.feature.projects.android_ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.project.space.components.ListTile

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(viewModel: ProjectsViewModel) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs: List<String> = listOf("My projects", "Others")

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
                .captionBarPadding()
        ) {
            CenterAlignedTopAppBar(
                title = { Text(text = "Projects") },
                actions = {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Image(imageVector = Icons.Default.Add, contentDescription = "Create Project")
                    }
                }
            )
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(),
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) }, selected = index == selectedTabIndex,
                        onClick = {
                            selectedTabIndex = index
                        }
                    )
                }
            }
/*            ListTile(
                title = "TestTitle",
                description = "Some dummy description that is very very long and doesn't fit in one line and overflows, " +
                        "Some dummy description that is very very long and doesn't fit in one line and overflows",
                onClick = {

                },
                trailing = { Image(imageVector = Icons.Default.ArrowRight, contentDescription = "Change project") }
            )
            ListTile(
                title = "TestTitle",
                description = "Some dummy description that is very very long and doesn't fit in one line and overflows, " +
                        "Some dummy description that is very very long and doesn't fit in one line and overflows",
                onClick = {

                },
                trailing = { Image(imageVector = Icons.Default.ArrowRight, contentDescription = "Change project") },
                divided = false
            )*/
        }
    }
}
