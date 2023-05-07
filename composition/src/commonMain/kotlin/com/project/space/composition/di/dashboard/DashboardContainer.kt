package com.project.space.composition.di.dashboard

import com.libraries.utils.PlatformScopeManager
import com.project.space.composition.di.RootContainer
import com.project.space.composition.navigation.Navigator
import com.project.space.feature.common.SelectedProjectManager
import com.project.space.feature.dashboard.*
import com.project.space.feature.dashboard.domain.DashboardDelegate
import com.project.space.feature.dashboard.domain.GetMembers
import com.project.space.feature.dashboard.domain.GetRisk
import com.project.space.feature.dashboard.domain.GetStatistics
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.project.ProjectService
import com.project.space.services.project.response.ProjectRisk
import com.project.space.services.projectmember.ProjectMemberService
import com.project.space.services.projectmemberlevel.response.ProjectMemberLevelName

class DashboardContainer(
    private val navigator: Navigator,
    private val container: RootContainer,
    private val projectService: ProjectService,
    private val projectMemberService: ProjectMemberService,
    private val selectedProjectManager: SelectedProjectManager,
) {
    private val scope: PlatformScopeManager = PlatformScopeManager()

    private val getStatisticsUseCase: GetStatistics by lazy {
        GetStatisticsUseCase(
            scope = scope,
            projectService = projectService,
            selectedProjectManager = selectedProjectManager
        )
    }

    private val getRiskUseCase: GetRisk by lazy {
        GetRiskUseCase(
            scope = scope,
            projectService = projectService,
            selectedProjectManager = selectedProjectManager
        )
    }

    private val getMembersUseCase: GetMembers by lazy {
        GetMembersUseCase(
            scope = scope,
            projectMemberService = projectMemberService,
            selectedProjectManager = selectedProjectManager
        )
    }

    fun presenter(): DashboardPresenter = DefaultDashboardPresenter(
        scope = scope,
        getSelectedProject = { selectedProjectManager.getSelectedProject() },
        getStatistics = getStatisticsUseCase,
        getRisk = getRiskUseCase,
        getMembers = getMembersUseCase,
        delegate = object : DashboardDelegate {
            override fun onNavigateToNewInvitation() {

            }
        }
    )
}

private class GetStatisticsUseCase(
    private val scope: PlatformScopeManager,
    private val projectService: ProjectService,
    private val selectedProjectManager: SelectedProjectManager
) : GetStatistics {
    override fun invoke(completion: (GetStatistics.Response) -> Unit) {
        val projectId: Int = selectedProjectManager.getSelectedProject()?.id ?: return

        scope.launch {
            return@launch when (val response = projectService.getProjectStatistics(id = projectId)) {
                is ProjectSpaceResult.Success -> {
                    completion(
                        GetStatistics.Response.Success(
                            data = Statistics(
                                totalTasks = response.data.data.totalTasks,
                                openTasks = response.data.data.openTasks,
                                closedTasks = response.data.data.closedTasks
                            )
                        )
                    )
                }
                is ProjectSpaceResult.Error -> {
                    completion(
                        GetStatistics.Response.Error(
                            title = "Unable to load statistics!", message = response.error.errors[0].message
                        )
                    )
                }
                else -> {
                    completion(
                        GetStatistics.Response.Error(
                            title = "Unable to load statistics!", message = "Something went wrong. Try again!"
                        )
                    )
                }
            }
        }
    }
}

private class GetRiskUseCase(
    private val scope: PlatformScopeManager,
    private val projectService: ProjectService,
    private val selectedProjectManager: SelectedProjectManager
) : GetRisk {
    override fun invoke(completion: (GetRisk.Response) -> Unit) {
        val projectId: Int = selectedProjectManager.getSelectedProject()?.id ?: return

        scope.launch {
            return@launch when (val response = projectService.getProjectMonthlyRisk(id = projectId)) {
                is ProjectSpaceResult.Success -> {
                    completion(
                        GetRisk.Response.Success(
                            data = Risk(
                                totalTasks = response.data.data.totalTasks,
                                openTasks = response.data.data.openTasks,
                                risk = when (response.data.data.risk) {
                                    ProjectRisk.UNKNOWN -> com.project.space.feature.dashboard.ProjectRisk.UNKNOWN
                                    ProjectRisk.NO_RISK -> com.project.space.feature.dashboard.ProjectRisk.NO_RISK
                                    ProjectRisk.LOW -> com.project.space.feature.dashboard.ProjectRisk.LOW
                                    ProjectRisk.MEDIUM -> com.project.space.feature.dashboard.ProjectRisk.MEDIUM
                                    ProjectRisk.HIGH -> com.project.space.feature.dashboard.ProjectRisk.HIGH
                                }
                            )
                        )
                    )
                }
                is ProjectSpaceResult.Error -> {
                    completion(
                        GetRisk.Response.Error(
                            title = "Unable to load risk!", message = response.error.errors[0].message
                        )
                    )
                }
                else -> {
                    completion(
                        GetRisk.Response.Error(
                            title = "Unable to load risk!", message = "Something went wrong. Try again!"
                        )
                    )
                }
            }
        }
    }
}

private class GetMembersUseCase(
    private val scope: PlatformScopeManager,
    private val projectMemberService: ProjectMemberService,
    private val selectedProjectManager: SelectedProjectManager
) : GetMembers {
    override fun invoke(completion: (GetMembers.Response) -> Unit) {
        val projectId: Int = selectedProjectManager.getSelectedProject()?.id ?: return

        scope.launch {
            return@launch when (val response = projectMemberService.getProjectMembers(projectId = projectId)) {
                is ProjectSpaceResult.Success -> {
                    completion(
                        GetMembers.Response.Success(
                            data = response.data.data.map {
                                Member(
                                    id = it.id,
                                    username = it.user.username,
                                    memberLevel = when (it.level.name) {
                                        ProjectMemberLevelName.MEMBER -> MemberLevel.MEMBER
                                        ProjectMemberLevelName.MODERATOR -> MemberLevel.MODERATOR
                                        ProjectMemberLevelName.OWNER -> MemberLevel.OWNER
                                    }
                                )
                            }
                        )
                    )
                }
                is ProjectSpaceResult.Error -> {
                    completion(
                        GetMembers.Response.Error(
                            title = "Unable to load project members!", message = response.error.errors[0].message
                        )
                    )
                }
                else -> {
                    completion(
                        GetMembers.Response.Error(
                            title = "Unable to load project members!", message = "Something went wrong. Try again!"
                        )
                    )
                }
            }
        }
    }

}
