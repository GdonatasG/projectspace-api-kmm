package com.project.space.feature.dashboard

actual interface DashboardView {
    fun display(state: StatisticsState)
    fun display(state: RiskState)
    fun display(state: MembersState)
    fun display(state: SelectedProjectState)
}

internal actual fun update(view: DashboardView?, state: StatisticsState) {
    view ?: return
    view.display(state)
}

internal actual fun update(view: DashboardView?, state: RiskState) {
    view ?: return
    view.display(state)
}

internal actual fun update(view: DashboardView?, state: MembersState) {
    view ?: return
    view.display(state)
}

internal actual fun update(view: DashboardView?, state: SelectedProjectState) {
    view ?: return
    view.display(state)
}
