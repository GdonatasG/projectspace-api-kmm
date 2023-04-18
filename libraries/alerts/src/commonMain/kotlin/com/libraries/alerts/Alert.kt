package com.libraries.alerts

class Alert(
    configuration: Alert.() -> Unit
) {
    var title: String = ""
    var message: String? = null
    var buttons: List<Button> = emptyList()

    init {
        apply(configuration)
    }

    interface Coordinator {
        fun show(alert: Alert)
        fun dismiss() {}
    }

    data class Button(
        private val configuration: Button.() -> Unit
    ) {
        var title: String = ""
        var event: Event = Event.DEFAULT
        var onClick: (() -> Unit)? = null

        init {
            apply(configuration)
        }

        enum class Event {
            CANCEL, DEFAULT, DESTRUCTIVE
        }

        companion object {
            fun Cancel(title: String = "Cancel", onClick: (() -> Unit)? = null) = Button {
                this.title = title
                this.event = Event.CANCEL
                this.onClick = onClick
            }
        }
    }

    fun send(event: Button.Event) {
        buttons.first { it.event == event }.onClick?.let { it() }
    }
}
