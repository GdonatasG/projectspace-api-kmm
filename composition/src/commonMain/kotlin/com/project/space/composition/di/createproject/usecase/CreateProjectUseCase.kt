package com.project.space.composition.di.createproject.usecase

import com.libraries.utils.PlatformScopeManager
import com.project.space.feature.createproject.domain.CreateProject
import com.project.space.services.common.ProjectSpaceResult
import com.project.space.services.project.ProjectService

class CreateProjectUseCase(
    private val scope: PlatformScopeManager,
    private val projectService: ProjectService
) : CreateProject {
    override fun invoke(name: String, description: String, completion: (CreateProject.Response) -> Unit) {
        scope.launch {
            return@launch when (val response = projectService.createProject(name = name) {
                if (description.trim().isNotEmpty()) {
                    description(description)
                }
            }) {
                is ProjectSpaceResult.Success -> {
                    completion(CreateProject.Response.Success)
                }
                is ProjectSpaceResult.Error -> {
                    completion(
                        CreateProject.Response.InputErrors(
                            data = response.error.errors.map {
                                CreateProject.InputError(
                                    input = it.type,
                                    message = it.message
                                )
                            }
                        )
                    )
                }
                else -> {
                    completion(CreateProject.Response.Error(message = "Something went wrong. Try again!"))
                }
            }
        }
    }
}
