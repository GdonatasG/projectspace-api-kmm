package com.libraries.alerts

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AlertControllerAdapter(private val alertState: AlertState) : AlertController {
    private val _navigationAction: MutableStateFlow<Unit?> = MutableStateFlow(null)
    private val _closeAction: MutableStateFlow<Unit?> = MutableStateFlow(null)

    override val navigationAction: StateFlow<Unit?>
        get() = _navigationAction.asStateFlow()

    override val closeAlert: StateFlow<Unit?>
        get() = _closeAction.asStateFlow()

    override fun close() {
        _closeAction.value = Unit
    }

    override fun show(alert: Alert) {
        alertState.setAlert(alert)
        _navigationAction.value = Unit
    }

    override fun resetNavigationAction() {
        _navigationAction.value = null
    }

    override fun resetCloseAction() {
        _closeAction.value = null
    }

}
