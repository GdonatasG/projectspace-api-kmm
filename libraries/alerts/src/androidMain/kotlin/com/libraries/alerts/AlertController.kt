package com.libraries.alerts

import kotlinx.coroutines.flow.StateFlow

interface AlertController {
    val navigationAction: StateFlow<Unit?>
    val closeAlert: StateFlow<Unit?>
    fun close()
    fun show(alert: Alert)
    fun resetNavigationAction()
    fun resetCloseAction()
}
