package com.project.space.feature.dashboard

import com.project.space.feature.common.domain.model.SelectedProject

sealed class StatisticsState {
    object Loading : StatisticsState()
    object Refreshing : StatisticsState()
    data class Content(val data: Statistics) : StatisticsState()
    data class Error(val title: String, val message: String) : StatisticsState()
}

data class Statistics(
    val totalTasks: Int,
    val openTasks: Int,
    val closedTasks: Int
)

sealed class RiskState {
    object Loading : RiskState()
    object Refreshing : RiskState()
    data class Content(val data: Risk) : RiskState()
    data class Error(val title: String, val message: String) : RiskState()
}

data class Risk(
    val totalTasks: Int,
    val openTasks: Int,
    val risk: ProjectRisk
)

enum class ProjectRisk(private val title: String) {
    UNKNOWN(title = "unknown"),
    NO_RISK(title = "no risk"),
    LOW(title = "low"),
    MEDIUM(title = "medium"),
    HIGH(title = "high");

    fun getVisualName(): String = this.title.uppercase()
}


sealed class MembersState {
    object Loading : MembersState()
    object Refreshing : MembersState()
    data class Content(val data: List<Member>) : MembersState()
    data class Error(val title: String, val message: String) : MembersState()
}

data class Member(
    val id: Int,
    val username: String,
    val memberLevel: MemberLevel
)

enum class MemberLevel(private val title: String) {
    MEMBER(title = "member"),
    MODERATOR(title = "moderator"),
    OWNER(title = "owner");

    fun getVisualName(): String = this.title.uppercase()
}

sealed class SelectedProjectState {
    data class None(val title: String, val message: String) : SelectedProjectState()
    data class Selected(val project: SelectedProject) : SelectedProjectState()
}

internal expect fun update(view: DashboardView?, state: StatisticsState)
internal expect fun update(view: DashboardView?, state: RiskState)
internal expect fun update(view: DashboardView?, state: MembersState)
internal expect fun update(view: DashboardView?, state: SelectedProjectState)

expect interface DashboardView
