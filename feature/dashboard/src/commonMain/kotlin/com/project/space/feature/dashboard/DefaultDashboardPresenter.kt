package com.project.space.feature.dashboard

import com.libraries.utils.PlatformScopeManager
import com.libraries.utils.ViewHolder
import com.project.space.feature.common.domain.model.SelectedProject
import com.project.space.feature.dashboard.domain.DashboardDelegate
import com.project.space.feature.dashboard.domain.GetMembers
import com.project.space.feature.dashboard.domain.GetRisk
import com.project.space.feature.dashboard.domain.GetStatistics

class DefaultDashboardPresenter(
    private val scope: PlatformScopeManager,
    private val getSelectedProject: (() -> SelectedProject?),
    private val getStatistics: GetStatistics,
    private val getRisk: GetRisk,
    private val getMembers: GetMembers,
    private val delegate: DashboardDelegate
) : DashboardPresenter() {
    override var viewHolder: ViewHolder<DashboardView> = ViewHolder()

    private val view
        get() = viewHolder.get()

    private var statisticsState: StatisticsState = StatisticsState.Loading
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    private var riskState: RiskState = RiskState.Loading
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    private var membersState: MembersState = MembersState.Loading
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    private var selectedProjectState: SelectedProjectState = SelectedProjectState.None(
        title = "No project selected!",
        message = "You do not have selected any project, feel free to select a project in Projects section below."
    )
        private set(newValue) {
            update(view, newValue)
            field = newValue
        }

    override fun onResume() {
        super.onResume()
        val newSelectedProject = getSelectedProject()

        val currentSelectedProject: SelectedProject? = (selectedProjectState as? SelectedProjectState.Selected)?.project

        if (newSelectedProject != null) {
            selectedProjectState = SelectedProjectState.Selected(project = newSelectedProject)

            if (newSelectedProject != currentSelectedProject) {
                statisticsState = StatisticsState.Loading

                riskState = RiskState.Loading

                membersState = MembersState.Loading
            }

            scope.cancelAllJobs()
            _getStatistics()
            _getRisk()
            _getMembers()

            return
        }

        selectedProjectState = SelectedProjectState.None(
            title = "No project selected!",
            message = "You do not have selected any project, feel free to select a project in Projects section below."
        )
    }

    override fun onRefresh() {
        scope.cancelAllJobs()
        statisticsState = StatisticsState.Refreshing
        _getStatistics()

        riskState = RiskState.Refreshing
        _getRisk()

        membersState = MembersState.Refreshing
        _getMembers()
    }

    override fun onNavigateToNewInvitation() {
        delegate.onNavigateToNewInvitation()
    }

    private fun _getStatistics() {
        getStatistics { response ->
            statisticsState = when (response) {
                is GetStatistics.Response.Success -> {
                    StatisticsState.Content(data = response.data)
                }
                is GetStatistics.Response.Error -> {
                    StatisticsState.Error(title = response.title, message = response.message)
                }
            }

        }
    }

    private fun _getRisk() {
        getRisk { response ->
            riskState = when (response) {
                is GetRisk.Response.Success -> {
                    RiskState.Content(data = response.data)
                }
                is GetRisk.Response.Error -> {
                    RiskState.Error(title = response.title, message = response.message)
                }
            }

        }
    }

    private fun _getMembers() {
        getMembers { response ->
            membersState = when (response) {
                is GetMembers.Response.Success -> {
                    MembersState.Content(data = response.data)
                }
                is GetMembers.Response.Error -> {
                    MembersState.Error(title = response.title, message = response.message)
                }
            }

        }
    }

    override fun dropView() {
        super.dropView()
        scope.cancelAllJobs()
    }
}
