package com.project.space.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalUriHandler
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.libraries.alerts.AlertController
import com.project.space.components.navigation.BackStackHandler
import com.project.space.components.navigation.NavigationAction
import com.project.space.components.navigation.Navigator
import com.project.space.presentation.NavGraphs
import com.project.space.presentation.destinations.AlertDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import org.koin.androidx.compose.get

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BaseNavigation(
    navigator: Navigator = get(),
    alertController: AlertController = get()
) {
    val navEngine =
        rememberAnimatedNavHostEngine(rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING)

    val navHostController = navEngine.rememberNavController()

    val navActions by navigator.navActions.collectAsState()

    val alertAction by alertController.navigationAction.collectAsState()

    val closeAlertAction by alertController.closeAlert.collectAsState()

    val navigateBack by navigator.navigateBackAction.collectAsState()

    val urlAction by navigator.urlNavActions.collectAsState()

    val uriHandler = LocalUriHandler.current

    LaunchedEffect(navigateBack) {
        navigateBack?.let {
            navHostController.navigateUp()
            navigator.resetBackAction()
        }
    }

    LaunchedEffect(urlAction) {
        urlAction?.let {
            uriHandler.openUri(it)
            navigator.resetNavigateToUrlAction()
        }
    }

    BackStackHandler(navHostController = navHostController)

    LaunchedEffect(navActions) {
        navActions?.let { action ->
            if (action.popUpAll) {
                navHostController.popBackStack()
            }
            navHostController.navigate(
                route = action.destination.route,
                navOptions = action.navOptions
            )
            navigator.resetNavigationAction()
        }
    }

    LaunchedEffect(alertAction) {
        alertAction?.let {
            navigator.navigate(NavigationAction(AlertDestination))
            alertController.resetNavigationAction()
        }
    }

    LaunchedEffect(closeAlertAction) {
        closeAlertAction?.let {
            navHostController.popBackStack()
            alertController.resetCloseAction()
        }
    }

    Scaffold {
        DestinationsNavHost(
            navGraph = NavGraphs.main,
            engine = navEngine,
            navController = navHostController
        )
    }

}
