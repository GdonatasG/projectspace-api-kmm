package com.project.space.feature.profile.domain

import com.project.space.feature.common.domain.model.CurrentUser

interface GetCurrentUser {
    operator fun invoke(): CurrentUser?
}
