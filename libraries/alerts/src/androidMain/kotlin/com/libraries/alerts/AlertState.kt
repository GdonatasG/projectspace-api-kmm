package com.libraries.alerts

interface AlertState {
    fun setAlert(alert: Alert)
    fun getTitle(): String
    fun getMessage(): String
    fun getButtons(): List<Alert.Button>
}
