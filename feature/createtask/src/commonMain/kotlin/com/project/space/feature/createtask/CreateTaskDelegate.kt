package com.project.space.feature.createtask

import com.project.space.feature.common.FiltersViewModel

interface CreateTaskDelegate {
    fun onNavigateBack()
    fun onNavigateToFilter(filter: FiltersViewModel)
}
