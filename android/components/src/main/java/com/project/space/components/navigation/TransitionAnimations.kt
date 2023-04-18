package com.project.space.components.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle

@OptIn(ExperimentalAnimationApi::class)
object SlideFromSideTransition : DestinationStyle.Animated {
    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition =
        SlideHorizontallyEnterTransition

    override fun AnimatedContentScope<NavBackStackEntry>.exitTransition(): ExitTransition = slideOutHorizontally(
        targetOffsetX = { -it / 2 },
        animationSpec = tween(
            durationMillis = AnimationDuration,
            easing = FastOutSlowInEasing
        )
    ) + fadeOut(animationSpec = tween(AnimationDuration))

    override fun AnimatedContentScope<NavBackStackEntry>.popExitTransition(): ExitTransition =
        SlideHorizontalPopExitTransition

    override fun AnimatedContentScope<NavBackStackEntry>.popEnterTransition(): EnterTransition = slideInHorizontally(
        initialOffsetX = { -it / 2 },
        animationSpec = tween(
            durationMillis = AnimationDuration,
            easing = FastOutSlowInEasing
        )
    )
}

@OptIn(ExperimentalAnimationApi::class)
object FadeInAndOutTransition : DestinationStyle.Animated {
    override fun AnimatedContentScope<NavBackStackEntry>.enterTransition(): EnterTransition = fadeIn(
        animationSpec = tween(AnimationDuration)
    )
}

private const val AnimationDuration = 400

@OptIn(ExperimentalAnimationApi::class)
private val SlideHorizontallyEnterTransition = slideInHorizontally(
    initialOffsetX = { it / 2 },
    animationSpec = tween(
        durationMillis = AnimationDuration,
        easing = FastOutSlowInEasing
    )
) + fadeIn(animationSpec = tween(AnimationDuration / 2))

@OptIn(ExperimentalAnimationApi::class)
private val SlideHorizontalPopExitTransition = slideOutHorizontally(
    targetOffsetX = { it / 2 },
    animationSpec = tween(
        durationMillis = AnimationDuration,
        easing = FastOutSlowInEasing
    )
) + fadeOut(animationSpec = tween(AnimationDuration / 2))
