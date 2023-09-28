package com.merseyside.merseyLib.messaging.core

actual abstract class NotificationInterceptor {

    actual abstract val tag: String

    actual open fun isResponsibleFor(notification: Notification): Boolean {
        TODO()
    }

    actual fun intercept(notification: Notification) {
        TODO()
    }
}