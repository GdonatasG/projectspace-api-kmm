package com.project.space.composition.navigation

import com.project.space.feature.common.FiltersViewModel

class FilterFlow(
    private val navigator: Navigator,
    private val viewModel: FiltersViewModel
) {
    fun start() {
        navigator.startFilter(viewModel)
    }
}
