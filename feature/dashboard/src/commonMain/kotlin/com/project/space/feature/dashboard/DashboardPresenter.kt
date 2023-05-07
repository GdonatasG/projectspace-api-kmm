package com.project.space.feature.dashboard

import com.libraries.utils.Presenter

abstract class DashboardPresenter: Presenter<DashboardView>() {
    abstract fun onRefresh()
    abstract fun onNavigateToNewInvitation()
}
