package com.libraries.alerts

class AlertStateAdapter : AlertState {
    private var alert: Alert? = null

    override fun setAlert(alert: Alert) {
        this.alert = alert
    }

    override fun getTitle(): String = alert?.title ?: ""

    override fun getMessage(): String = alert?.message ?: ""

    override fun getButtons(): List<Alert.Button> = alert?.buttons ?: emptyList()
}

