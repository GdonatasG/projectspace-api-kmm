package com.project.space.feature.common.android_ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.project.space.components.button.NavigateBackButton
import com.project.space.components.view.EmptyView
import com.project.space.components.view.ErrorView
import com.project.space.components.view.LoadingView
import com.project.space.feature.common.FilterState
import com.project.space.feature.common.RemoteSingleChoiceFiltersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(viewModel: FilterViewModel) {
    val filter = viewModel.viewModel
    val state by filter.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = filter.title) },
                navigationIcon = {
                    NavigateBackButton(
                        onClick = {
                            filter.onNavigateBack()
                        }
                    )
                },
                actions = {
                    if (state is FilterState.Content) {
                        TextButton(
                            onClick = { filter.onApply() }
                        ) {
                            Text(text = "Apply")
                        }
                    }

                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .imePadding()
                .captionBarPadding()
        ) {
            when (val type = state) {
                is FilterState.Content -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxHeight(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        itemsIndexed(
                            type.list,
                            key = { _, filterViewModel -> filterViewModel.id }) { index, filterViewModel ->
                            val value by filterViewModel.state.collectAsState()
                            if (filter is RemoteSingleChoiceFiltersViewModel) {
                                RadioFilter(
                                    value = value.selected,
                                    name = value.name,
                                    onClick = {
                                        filter.onSelect(filterViewModel)
                                    }
                                )
                            }
                            if (index < type.list.size - 1) {
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
                is FilterState.Loading -> LoadingView()
                is FilterState.Error -> ErrorView(title = type.title, message = type.message, onRetry = {
                    type.onRetry?.let {
                        it()
                    }
                })
                is FilterState.Empty -> EmptyView(title = type.title, message = type.message, onRefresh = {
                    type.onRetry?.let {
                        it()
                    }
                })
            }

        }
    }
}

@Composable
private fun RadioFilter(value: Boolean, name: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
            .requiredHeight(ButtonDefaults.MinHeight)
    ) {
        Text(
            text = name,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = if (value) FontWeight.W600 else FontWeight.Normal
        )
        Spacer(modifier = Modifier.width(8.dp))
        RadioButton(selected = value, onClick = null)
    }
}
