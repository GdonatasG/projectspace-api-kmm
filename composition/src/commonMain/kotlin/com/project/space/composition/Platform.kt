package com.project.space.composition

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform