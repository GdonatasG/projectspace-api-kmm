package com.project.space.feature.common.android_ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.project.space.feature.common.FiltersViewModel

class FilterViewModel(val viewModel: FiltersViewModel) : ViewModel(),
    LifecycleEventObserver {

    init {
        viewModel.onAppear()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> {}
            Lifecycle.Event.ON_STOP -> {}
            else -> {}
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModel.onClear()
    }
}
