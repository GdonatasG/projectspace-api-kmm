package com.project.space.composition.di.projects.usecase

import com.libraries.utils.PlatformScopeManager
import com.project.space.feature.projects.Tab
import com.project.space.feature.projects.domain.GetProjects
import com.project.space.feature.projects.domain.Project
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.project.ProjectService
import com.project.space.services.project.response.ProjectResponse

class GetProjectsUseCase(
    private val scope: PlatformScopeManager,
    private val projectService: ProjectService
) : GetProjects {
    override fun invoke(tab: Tab, completion: (GetProjects.Response) -> Unit) {
        scope.launch {
            return@launch when (val projectsResponse = projectService.getSessionUserAvailableProjects {
                owned(tab == Tab.MY_PROJECTS)
            }) {
                is ProjectSpaceResult.Success -> {
                    completion(GetProjects.Response.Success(data = projectsResponse.data.data.map { it.toDomain() }))
                }
                is ProjectSpaceResult.Error -> {
                    completion(GetProjects.Response.Error(message = projectsResponse.error.errors[0].message))
                }
                else -> {
                    completion(GetProjects.Response.Error(message = "Something went wrong. Try again!"))
                }
            }
        }
    }
}

private fun ProjectResponse.toDomain(): Project =
    Project(id = this.id, name = this.name, description = this.description ?: "")
