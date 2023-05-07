package com.project.space.feature.dashboard

actual interface DashboardView {
    fun display(dashboardViewStatisticsLoading: StatisticsState.Loading)
    fun display(dashboardViewStatisticsRefreshing: StatisticsState.Refreshing)
    fun display(dashboardViewStatisticsContent: StatisticsState.Content)
    fun display(dashboardViewStatisticsError: StatisticsState.Error)

    fun display(dashboardViewRiskLoading: RiskState.Loading)
    fun display(dashboardViewRiskRefreshing: RiskState.Refreshing)
    fun display(dashboardViewRiskContent: RiskState.Content)
    fun display(dashboardViewRiskError: RiskState.Error)

    fun display(dashboardViewMembersLoading: MembersState.Loading)
    fun display(dashboardViewMembersRefreshing: MembersState.Refreshing)
    fun display(dashboardViewMembersContent: MembersState.Content)
    fun display(dashboardViewMembersError: MembersState.Error)

    fun display(dashboardViewSelectedProjectNone: SelectedProjectState.None)
    fun display(dashboardViewSelectedProjectSelected: SelectedProjectState.Selected)
}

internal actual fun update(view: DashboardView?, state: StatisticsState) {
    view ?: return
    when (state) {
        is StatisticsState.Loading -> view.display(state)
        is StatisticsState.Refreshing -> view.display(state)
        is StatisticsState.Content -> view.display(state)
        is StatisticsState.Error -> view.display(state)
    }
}

internal actual fun update(view: DashboardView?, state: RiskState) {
    view ?: return
    when (state) {
        is RiskState.Loading -> view.display(state)
        is RiskState.Refreshing -> view.display(state)
        is RiskState.Content -> view.display(state)
        is RiskState.Error -> view.display(state)
    }
}

internal actual fun update(view: DashboardView?, state: MembersState) {
    view ?: return
    when (state) {
        is MembersState.Loading -> view.display(state)
        is MembersState.Refreshing -> view.display(state)
        is MembersState.Content -> view.display(state)
        is MembersState.Error -> view.display(state)
    }
}

internal actual fun update(view: DashboardView?, state: SelectedProjectState) {
    view ?: return
    when (state) {
        is SelectedProjectState.None -> view.display(state)
        is SelectedProjectState.Selected -> view.display(state)
    }
}
