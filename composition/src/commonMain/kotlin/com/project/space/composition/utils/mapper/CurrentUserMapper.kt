package com.project.space.composition.utils.mapper

import com.project.space.feature.common.domain.model.CurrentUser
import com.project.space.services.user.response.UserResponse
import com.project.space.services.user.response.UserRole

fun UserResponse.toDomain(): CurrentUser = CurrentUser(
    id = this.id, username = this.username, email = this.email, role = this.role.toDomain()
)

fun UserRole.toDomain(): com.project.space.feature.common.domain.model.UserRole = when (this) {
    UserRole.USER -> com.project.space.feature.common.domain.model.UserRole.USER
    UserRole.ADMIN -> com.project.space.feature.common.domain.model.UserRole.ADMIN
}

