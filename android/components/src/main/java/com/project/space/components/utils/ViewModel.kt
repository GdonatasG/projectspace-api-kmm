package com.project.space.components.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.ParametersDefinition

@Composable
inline fun <reified T : ViewModel> getLifecycleViewModel(noinline parameters: ParametersDefinition? = null): T {
    val viewModel = getViewModel<T>(parameters = parameters)

    if (viewModel !is LifecycleEventObserver) {
        throw java.lang.RuntimeException("ViewModel is not LifecycleEventObserver")
    }

    RegisterLifecycleObserver(observer = viewModel)

    return viewModel
}

@Composable
fun RegisterLifecycleObserver(observer: LifecycleEventObserver) {
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(true) {
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
