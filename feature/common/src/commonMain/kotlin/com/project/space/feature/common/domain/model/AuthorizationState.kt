package com.project.space.feature.common.domain.model

@kotlinx.serialization.Serializable
data class AuthorizationState(val accessToken: String) {
    companion object {
        fun empty(): AuthorizationState = AuthorizationState(accessToken = "")
    }
}

fun AuthorizationState?.isLoggedIn() = this != null
