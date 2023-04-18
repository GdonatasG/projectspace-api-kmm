package com.libraries.alerts

import com.libraries.alerts.AlertController

class AlertCoordinator(private val alertController: AlertController) : Alert.Coordinator {
    override fun show(alert: Alert) {
        alertController.show(alert)
    }
}
